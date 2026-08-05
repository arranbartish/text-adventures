package au.bartish.game.npc;

import au.bartish.game.chat.ChatConfiguration;
import au.bartish.game.chat.ChatMessage;
import au.bartish.game.chat.ChatRole;
import au.bartish.game.chat.ScriptedChatClient;
import au.bartish.game.chat.SilentChatClient;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversationSessionTest {

    private final GameStateSnapshot state = new GameStateSnapshot("nothing", "no progress");

    @Test
    public void leave_ends_the_session_and_prints_the_farewell() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("hello there");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ConversationSession session = session(client, buffer, "leave\n");
        session.run(npc, state);

        assertThat(session.getEndReason()).isEqualTo(ConversationSession.EndReason.LEFT);
        assertThat(buffer.toString(StandardCharsets.UTF_8)).contains(npc.getFarewellMessage());
    }

    @Test
    public void leave_is_case_and_whitespace_insensitive() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("hello there");

        ConversationSession session = session(client, new ByteArrayOutputStream(), "  LEAVE  \n");
        session.run(npc, state);

        assertThat(session.getEndReason()).isEqualTo(ConversationSession.EndReason.LEFT);
    }

    @Test
    public void non_leave_input_is_forwarded_and_the_reply_is_printed() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("opening line", "a real reply");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ConversationSession session = session(client, buffer, "what killed the wizard?\nleave\n");
        session.run(npc, state);

        assertThat(buffer.toString(StandardCharsets.UTF_8)).contains("a real reply");
    }

    @Test
    public void history_grows_system_then_opening_cue_then_alternating_turns() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("opening reply", "second reply");

        ConversationSession session = session(client, new ByteArrayOutputStream(), "a question\nleave\n");
        session.run(npc, state);

        var history = session.getHistory();
        assertThat(history.get(0).getRole()).isEqualTo(ChatRole.SYSTEM);
        assertThat(history.get(1)).isEqualTo(ChatMessage.user(npc.getOpeningCue()));
        assertThat(history.get(2)).isEqualTo(ChatMessage.assistant("opening reply"));
        assertThat(history.get(3)).isEqualTo(ChatMessage.user("a question"));
        assertThat(history.get(4)).isEqualTo(ChatMessage.assistant("second reply"));
    }

    @Test
    public void the_turn_cap_ends_the_session_with_a_wrap_up() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("opening reply", "reply two", "reply three");
        ChatConfiguration cappedAtTwoTurns = fakeConfig(2);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ConversationSession session = new ConversationSession(
                client, cappedAtTwoTurns, scannerOf("question one\nquestion two\nquestion three\n"), toStream(buffer));
        session.run(npc, state);

        assertThat(session.getEndReason()).isEqualTo(ConversationSession.EndReason.TURN_CAP_REACHED);
        assertThat(buffer.toString(StandardCharsets.UTF_8)).contains(npc.getFarewellMessage());
    }

    @Test
    public void a_chat_exception_on_the_opening_reply_ends_the_session_with_the_unavailable_message() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ConversationSession session = session(new SilentChatClient(), buffer, "");
        session.run(npc, state);

        assertThat(session.getEndReason()).isEqualTo(ConversationSession.EndReason.CHAT_FAILED);
        assertThat(buffer.toString(StandardCharsets.UTF_8)).contains(npc.getUnavailableMessage());
    }

    @Test
    public void a_chat_exception_mid_session_ends_the_session_with_a_generic_fade_out() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("opening reply");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ConversationSession session = session(client, buffer, "a question that has no scripted reply\n");
        session.run(npc, state);

        assertThat(session.getEndReason()).isEqualTo(ConversationSession.EndReason.CHAT_FAILED);
        assertThat(buffer.toString(StandardCharsets.UTF_8))
                .contains(ConversationSession.MID_SESSION_FAILURE_MESSAGE);
    }

    @Test
    public void blank_input_does_not_call_the_chat_client() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("opening reply");

        ConversationSession session = session(client, new ByteArrayOutputStream(), "   \n\nleave\n");
        session.run(npc, state);

        assertThat(client.callCount()).isEqualTo(1);
    }

    private ConversationSession session(au.bartish.game.chat.ChatClient client, ByteArrayOutputStream buffer, String input) {
        return new ConversationSession(client, fakeConfig(20), scannerOf(input), toStream(buffer));
    }

    private Scanner scannerOf(String input) {
        return new Scanner(input);
    }

    private PrintStream toStream(ByteArrayOutputStream buffer) {
        return new PrintStream(buffer, true, StandardCharsets.UTF_8);
    }

    private ChatConfiguration fakeConfig(int maxTurns) {
        var dotEnv = au.bartish.game.config.DotEnv.load(
                java.nio.file.Path.of("/nonexistent/.env"),
                key -> "NPC_MAX_TURNS".equals(key) ? String.valueOf(maxTurns) : null);
        return ChatConfiguration.fromDotEnv(dotEnv);
    }
}
