package au.bartish.game;

import au.bartish.game.basic.AnotherSimpleLocation;
import au.bartish.game.basic.SimpleArtifact;
import au.bartish.game.basic.SimpleGame;
import au.bartish.game.exit.Exit;
import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Supplier;

import static au.bartish.game.basic.SimpleArtifact.DEFAULT;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class LoopingGameTest {


    @Test
    public void will_welcome() {
        String input = "yes";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(context.getGameOutput(), containsString("A Welcome"));
    }

    @Test
    public void will_change_location_after_tick() {
        String input = "yes";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(loop.getCurrentLocation().getDisplayName(), is(new AnotherSimpleLocation().getDisplayName()));
    }

    @Test
    public void will_take_item_to_inventory_after_tick() {
        String input = "take something";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(loop.getInventory().listItems(), containsString("something"));
    }

    @Test
    public void will_take_item_from_location_after_tick() {
        String input = "take something";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(loop.getCurrentLocation().itemsCount(), is(0));
    }

    @Test
    public void will_print_the_sack_after_two_ticks() {
        String input = "take something\nsack";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(context.getGameOutput(), containsString("your sack contains:\n- something"));
    }

    @Test
    public void will_print_the_sack_after_one_tick() {
        String input = "sack";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(context.getGameOutput(), containsString("your sack has nothing in it"));
    }


    @Test
    public void will_print_the_empty_sack_with_inventory_after_one_tick() {
        String input = "Inventory";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(context.getGameOutput(), containsString("your sack has nothing in it"));
    }

    @Test
    public void will_inform_me_that_gun_is_not_in_location() {
        String input = "take gun";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(context.getGameOutput(), containsString("gun is not in the Simple Location"));
    }

    @Test
    @Ignore
    public void will_turn_berries_into_cranberries() {
        String input = "drop something\ntake berries\nput berries in water\ntake cranberries\ninventory";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(context.getGameOutput(), containsString("your sack contains:\n- cranberries"));
    }

    private class ExecuteXTimes implements Supplier<Boolean> {

        private int times;

        public ExecuteXTimes(int times) {
            this.times = times;
        }

        @Override
        public Boolean get() {
            return (times-- != 0);
        }
    }

    private class GameContext {
        private final Scanner scanner;
        private final OutputStream stream;
        private final PrintStream printStream;
        private final Game game;
        private final LoopingGame<SimpleArtifact> loop;


        public GameContext(String gameInput) {
            this(cleanInput(gameInput), countMatches(cleanInput(gameInput), '\n'));
        }

        private GameContext(String gameInput, int iterations) {
            scanner = new Scanner(new ByteArrayInputStream(gameInput.getBytes()));
            stream = new ByteArrayOutputStream();
            printStream = new PrintStream(stream);
            game = new SimpleGame(scanner, printStream);
            loop = new LoopingGame<>(
                    DEFAULT,
                    game,
                    scanner,
                    printStream);
            loop.setWillContinue(new ExecuteXTimes(iterations));
        }


        public LoopingGame<SimpleArtifact> getLoop() {
            return loop;
        }



        public String getGameOutput() {
            return stream.toString();
        }
    }

    public static String cleanInput(String input) {
        return trim(input) + "\n";
    }
}