package au.bartish.game.npc;

import au.bartish.game.chat.ChatConfiguration;
import au.bartish.game.chat.ScriptedChatClient;
import au.bartish.game.config.DotEnv;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversationRunnerTest {

    private final GameStateSnapshot state = new GameStateSnapshot("nothing", "no progress");

    @Test
    public void talk_to_elf_resolves_via_alias_and_runs_a_conversation() {
        TestNpc elf = new TestNpc("Dark Elf", "elf", "dark elf");
        NpcRoster roster = new NpcRoster(elf);
        ScriptedChatClient client = new ScriptedChatClient("hello adventurer");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ConversationRunner runner = runner(client, buffer, "leave\n");
        boolean handled = runner.handle("talk to elf", roster, state);

        assertThat(handled).isTrue();
        assertThat(buffer.toString(StandardCharsets.UTF_8)).contains("hello adventurer");
    }

    @Test
    public void alias_matching_is_case_and_whitespace_insensitive() {
        TestNpc elf = new TestNpc("Dark Elf", "elf");
        NpcRoster roster = new NpcRoster(elf);
        ScriptedChatClient client = new ScriptedChatClient("hi");

        boolean handled = runner(client, new ByteArrayOutputStream(), "leave\n")
                .handle("  TALK TO   elf  ".trim(), roster, state);

        assertThat(handled).isTrue();
    }

    @Test
    public void talk_to_unknown_npc_is_not_handled() {
        NpcRoster roster = new NpcRoster(new TestNpc("Dark Elf", "elf"));
        ScriptedChatClient client = new ScriptedChatClient();

        boolean handled = runner(client, new ByteArrayOutputStream(), "")
                .handle("talk to nobody", roster, state);

        assertThat(handled).isFalse();
        assertThat(client.callCount()).isZero();
    }

    @Test
    public void an_unavailable_npc_is_not_resolvable() {
        TestNpc spirit = new TestNpc("Vegetable Spirit", "spirit");
        spirit.setAvailable(false);
        NpcRoster roster = new NpcRoster(spirit);
        ScriptedChatClient client = new ScriptedChatClient();

        boolean handled = runner(client, new ByteArrayOutputStream(), "")
                .handle("talk to spirit", roster, state);

        assertThat(handled).isFalse();
    }

    @Test
    public void plain_talk_with_no_target_is_not_handled() {
        NpcRoster roster = new NpcRoster(new TestNpc("Dark Elf", "elf"));
        ScriptedChatClient client = new ScriptedChatClient();

        boolean handled = runner(client, new ByteArrayOutputStream(), "")
                .handle("talk", roster, state);

        assertThat(handled).isFalse();
    }

    @Test
    public void unrelated_input_is_not_handled() {
        NpcRoster roster = new NpcRoster(new TestNpc("Dark Elf", "elf"));
        ScriptedChatClient client = new ScriptedChatClient();

        boolean handled = runner(client, new ByteArrayOutputStream(), "")
                .handle("look around", roster, state);

        assertThat(handled).isFalse();
    }

    private ConversationRunner runner(au.bartish.game.chat.ChatClient client, ByteArrayOutputStream buffer, String input) {
        return new ConversationRunner(client, fakeConfig(), new Scanner(input),
                new PrintStream(buffer, true, StandardCharsets.UTF_8));
    }

    private ChatConfiguration fakeConfig() {
        return ChatConfiguration.fromDotEnv(DotEnv.load(Path.of("/nonexistent/.env"), key -> null));
    }
}
