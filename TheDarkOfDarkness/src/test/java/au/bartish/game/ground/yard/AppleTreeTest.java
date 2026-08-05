package au.bartish.game.ground.yard;

import au.bartish.game.Backpack;
import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.chat.ChatConfiguration;
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

public class AppleTreeTest {

    @Test
    public void talk_to_elf_starts_a_conversation_and_leave_returns_to_the_apple_tree() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        House house = houseWith(new ScriptedChatClient("Bah, another visitor."), buffer, "leave\n");
        AppleTree appleTree = new AppleTree(house);

        Location afterOpening = appleTree.doAction("talk to elf");

        assertThat(afterOpening).isSameAs(appleTree);
        assertThat(buffer.toString(StandardCharsets.UTF_8)).contains("Bah, another visitor.");
    }

    @Test
    public void leave_outside_a_conversation_still_returns_to_the_yard() {
        House house = houseWith(new SilentChatClient(), new ByteArrayOutputStream(), "");
        AppleTree appleTree = new AppleTree(house);

        Location result = appleTree.doAction("leave");

        assertThat(result).isSameAs(house.get("yard"));
    }

    @Test
    public void a_silent_chat_client_reports_the_elf_as_unavailable_and_stays_put() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        House house = houseWith(new SilentChatClient(), buffer, "");
        AppleTree appleTree = new AppleTree(house);

        Location result = appleTree.doAction("talk to elf");

        assertThat(result).isSameAs(appleTree);
        assertThat(buffer.toString(StandardCharsets.UTF_8)).contains("asleep");
    }

    private House houseWith(au.bartish.game.chat.ChatClient chatClient, ByteArrayOutputStream buffer, String input) {
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        ChatConfiguration configuration = ChatConfiguration.fromDotEnv(DotEnv.load(Path.of("/nonexistent/.env"), key -> null));
        return new House(out, new Scanner(input), chatClient, configuration, new Backpack());
    }
}
