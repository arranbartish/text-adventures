package au.bartish.game.npc;

import au.bartish.game.chat.ChatConfiguration;
import au.bartish.game.chat.ChatMessage;
import au.bartish.game.chat.ChatRole;
import au.bartish.game.chat.ScriptedChatClient;
import au.bartish.game.chat.SilentChatClient;
import au.bartish.game.config.DotEnv;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Proves the behaviour this revision exists for: a conversation can be left
 * and re-entered any number of times, each time as a genuinely fresh session,
 * with only the small {@link NpcMemory} flags surviving in between - and that
 * two different NPCs never see each other's history.
 */
public class ConversationRestartTest {

    private final GameStateSnapshot state = new GameStateSnapshot("nothing", "no progress");

    @Test
    public void re_entering_after_leave_starts_with_a_fresh_empty_history() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("first session opening", "second session opening");

        ConversationSession first = session(client, "leave\n");
        first.run(npc, state);
        assertThat(first.getHistory()).hasSize(3);
        assertThat(first.getHistory().get(1)).isEqualTo(ChatMessage.user(npc.getOpeningCue()));

        ConversationSession second = session(client, "leave\n");
        second.run(npc, state);

        assertThat(second.getHistory()).hasSize(3);
        assertThat(second.getHistory().get(1)).isEqualTo(ChatMessage.user(npc.getOpeningCue()));
    }

    @Test
    public void the_turn_counter_resets_on_re_entry() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ChatConfiguration cappedAtOneTurn = fakeConfig(1);
        ScriptedChatClient client = new ScriptedChatClient("opening one", "opening two");

        ConversationSession first = new ConversationSession(client, cappedAtOneTurn, new Scanner(""), silentOut());
        first.run(npc, state);
        assertThat(first.getEndReason()).isEqualTo(ConversationSession.EndReason.TURN_CAP_REACHED);

        ConversationSession second = new ConversationSession(client, cappedAtOneTurn, new Scanner(""), silentOut());
        second.run(npc, state);

        assertThat(second.getEndReason()).isEqualTo(ConversationSession.EndReason.TURN_CAP_REACHED);
        assertThat(second.getHistory()).hasSize(3);
    }

    @Test
    public void the_second_sessions_prompt_uses_the_returning_visit_memory_line() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("first opening", "second opening");

        session(client, "leave\n").run(npc, state);
        ConversationSession second = session(client, "leave\n");
        second.run(npc, state);

        String secondSystemPrompt = second.getHistory().get(0).getContent();
        assertThat(secondSystemPrompt).contains("met this player before");
    }

    @Test
    public void a_session_killed_by_a_chat_exception_can_be_re_entered_and_then_succeed() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");

        ConversationSession failed = session(new SilentChatClient(), "leave\n");
        failed.run(npc, state);
        assertThat(failed.getEndReason()).isEqualTo(ConversationSession.EndReason.CHAT_FAILED);
        assertThat(npc.getMemory().hasSpoken()).isFalse();

        ScriptedChatClient workingClient = new ScriptedChatClient("it works now");
        ConversationSession retry = session(workingClient, "leave\n");
        retry.run(npc, state);

        assertThat(retry.getEndReason()).isEqualTo(ConversationSession.EndReason.LEFT);
        assertThat(npc.getMemory().hasSpoken()).isTrue();
    }

    @Test
    public void one_npcs_history_never_appears_in_another_npcs_session() {
        TestNpc elf = new TestNpc("Dark Elf", "elf");
        TestNpc spirit = new TestNpc("Vegetable Spirit", "spirit");
        ScriptedChatClient client = new ScriptedChatClient("elf opening", "spirit opening");

        ConversationSession elfSession = session(client, "leave\n");
        elfSession.run(elf, state);

        ConversationSession spiritSession = session(client, "leave\n");
        spiritSession.run(spirit, state);

        assertThat(spiritSession.getHistory().get(1)).isEqualTo(ChatMessage.user(spirit.getOpeningCue()));
        assertThat(spiritSession.getHistory())
                .noneMatch(message -> message.getRole() == ChatRole.USER
                        && message.getContent().equals(elf.getOpeningCue()));
    }

    @Test
    public void memory_flags_are_set_on_the_leave_path() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        ScriptedChatClient client = new ScriptedChatClient("opening reply");

        assertThat(npc.getMemory().hasSpoken()).isFalse();
        assertThat(npc.getMemory().hasBeenBriefed()).isFalse();

        session(client, "leave\n").run(npc, state);

        assertThat(npc.getMemory().hasSpoken()).isTrue();
        assertThat(npc.getMemory().hasBeenBriefed()).isTrue();
    }

    private ConversationSession session(au.bartish.game.chat.ChatClient client, String input) {
        return new ConversationSession(client, fakeConfig(20), new Scanner(input), silentOut());
    }

    private PrintStream silentOut() {
        return new PrintStream(new ByteArrayOutputStream(), true, StandardCharsets.UTF_8);
    }

    private ChatConfiguration fakeConfig(int maxTurns) {
        DotEnv dotEnv = DotEnv.load(
                Path.of("/nonexistent/.env"),
                key -> "NPC_MAX_TURNS".equals(key) ? String.valueOf(maxTurns) : null);
        return ChatConfiguration.fromDotEnv(dotEnv);
    }
}
