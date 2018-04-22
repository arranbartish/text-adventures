package au.bartish.game;

import au.bartish.game.basic.AnotherSimpleLocation;
import au.bartish.game.basic.SimpleArtifact;
import au.bartish.game.basic.SimpleGame;
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
        GameContext context = playGame("yes");
        assertThat(context.getGameOutput(), containsString("A Welcome"));
    }

    @Test
    public void will_change_location_after_tick() {
        GameContext context = playGame("yes");
        assertThat(context.getLoop().getCurrentLocation().getDisplayName(), is(new AnotherSimpleLocation().getDisplayName()));
    }

    @Test
    public void will_take_item_to_inventory_after_tick() {
        GameContext context = playGame("take something");
        assertThat(context.getLoop().getInventory().listItems(), containsString("something"));
    }

    @Test
    public void will_take_item_from_location_after_tick() {
        GameContext context = playGame("take something");
        assertThat(context.getLoop().getCurrentLocation().itemsCount(), is(0));
    }

    @Test
    public void will_print_the_sack_after_two_ticks() {
        GameContext context = playGame("take something\nsack");
        assertThat(context.getGameOutput(), containsString("your sack contains:\n- something"));
    }

    @Test
    public void will_print_the_sack_after_one_tick() {
        GameContext context = playGame("sack");
        assertThat(context.getGameOutput(), containsString("your sack has nothing in it"));
    }


    @Test
    public void will_print_the_empty_sack_with_inventory_after_one_tick() {
        GameContext context = playGame("Inventory");
        assertThat(context.getGameOutput(), containsString("your sack has nothing in it"));
    }

    @Test
    public void will_inform_me_that_gun_is_not_in_location() {
        GameContext context = playGame("take gun");
        assertThat(context.getGameOutput(), containsString("gun is not in the Simple Location"));
    }

    @Test
    public void will_drop_something_and_inventory_will_be_empty() {
        GameContext context = playGame("take something\ndrop something\ninventory");
        assertThat(context.getGameOutput(), containsString("your sack has nothing in it"));
    }


    @Test
    public void will_not_drop_something_when_inventory_is_empty() {
        GameContext context = playGame("drop something");
        assertThat(context.getGameOutput(), containsString("something is not in your sack"));
    }

    @Test
    public void will_look_around_an_empty_location() {
        GameContext context = playGame("take something\nlook around");
        assertThat(context.getGameOutput(), containsString("your in a Simple Location and it has nothing in it"));
    }

    @Test
    public void will_initialize_simple_location_with_something() {
        GameContext context = initGame(null);
        assertThat(context.getCurrentLocation().listItems(), containsString("- something"));
    }

    @Test
    public void will_look_around_a_location_with_something() {
        GameContext context = playGame("look around");
        assertThat(context.getGameOutput(), containsString("your in a Simple Location and it contains:\n- something"));
    }

    @Test
    @Ignore("Items can be fixtures")
    public void will_not_be_able_to_take_perminent_items() {
        GameContext context = playGame("yes\ntake oven");
        assertThat(context.getGameOutput(), containsString("You cannot take the oven, it is too heavy"));
    }

    @Test
    @Ignore
    public void will_turn_berries_into_cranberries() {
        String input = "take berries\nput berries in water\ntake cranberries\ninventory";
        GameContext context = new GameContext(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        assertThat(context.getGameOutput(), containsString("your sack contains:\n- cranberries"));
    }

    private GameContext playGame(String input) {
        GameContext context = initGame(input);
        LoopingGame<SimpleArtifact> loop = context.getLoop();

        loop.execute();
        return context;
    }

    private GameContext initGame(String input) {
        return new GameContext(input);
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


        public Location getCurrentLocation() {
            return getLoop().getCurrentLocation();
        }

        public String getGameOutput() {
            return stream.toString();
        }
    }

    public static String cleanInput(String input) {
        return trim(input) + "\n";
    }
}