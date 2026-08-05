package au.bartish.game;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Supplier;

import static au.bartish.game.Artifact.DEFAULT;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AllLocationsTest {

    @Test
    public void will_reach_wardrobe() {
        assertLocationReached("yes\nnorth\nlook around", "wardrobe");
    }

    @Test
    public void will_reach_kitchen() {
        assertLocationReached("yes\nwest\nlook around", "Kitchen");
    }

    @Test
    public void will_reach_living_room() {
        assertLocationReached("yes\neast\nlook around", "living room");
    }

    @Test
    public void will_reach_toy_chest() {
        assertLocationReached("yes\neast\nchest\nlook around", "toy chest");
    }

    @Test
    public void will_reach_yard() {
        assertLocationReached("yes\neast\nexit\nlook around", "yard");
    }

    @Test
    public void will_reach_apple_tree() {
        assertLocationReached("yes\neast\nexit\ntree\nlook around", "apple tree");
    }

    @Test
    public void will_reach_shed() {
        assertLocationReached("yes\neast\nexit\nshed\nlook around", "shed");
    }

    @Test
    public void will_reach_dungeon_stairway() {
        assertLocationReached("yes\neast\nexit\nshed\ndown\nlook around", "ominous stairway");
    }

    @Test
    public void will_reach_dungeon_cells() {
        assertLocationReached("yes\neast\nexit\nshed\ndown\ndescend\nlook around", "dungeon cells");
    }

    @Test
    public void will_reach_summoning_chamber() {
        assertLocationReached("yes\neast\nexit\nshed\ndown\ndescend\npass\nlook around", "summoning chamber");
    }

    @Test
    public void will_reach_restored_incantation() {
        assertLocationReached("yes\neast\nexit\nshed\ndown\ndescend\npass\nrestore\nlook around", "restored incantation");
    }

    @Test
    public void will_reach_victory() {
        // Note: no further action is sent once Victory is reached, since any
        // action there ends the game via System.exit. We assert directly on
        // the resulting location instead of using "look around".
        GameContext context = playGame("yes\neast\nexit\nshed\ndown\ndescend\npass\nrestore\nrecite");
        assertThat(context.getCurrentLocation().getDisplayName(), is("demonic castle"));
    }

    @Test
    public void will_reach_landing() {
        assertLocationReached("yes\nup\nlook around", "first floor landing");
    }

    @Test
    public void will_reach_library() {
        assertLocationReached("yes\nup\nwest\nlook around", "library");
    }

    @Test
    public void will_reach_bookshelf() {
        assertLocationReached("yes\nup\nwest\nshelf\nlook around", "bookshelf");
    }

    @Test
    public void will_reach_study() {
        assertLocationReached("yes\nup\nnorth\nlook around", "study");
    }

    @Test
    public void will_reach_sunlight_stream() {
        assertLocationReached("yes\nup\nnorth\nsunlight\nlook around", "sunlight stream");
    }

    @Test
    public void will_reach_forge() {
        assertLocationReached("yes\nup\nnorth\nforge\nlook around", "forge");
    }

    @Test
    public void will_reach_bullet_mold() {
        assertLocationReached("yes\nup\nnorth\nmold\nlook around", "bullet mold");
    }

    @Test
    public void will_reach_bathroom() {
        assertLocationReached("yes\nup\nbathroom\nlook around", "bathroom");
    }

    @Test
    public void will_reach_ballroom() {
        assertLocationReached("yes\nup\nballroom\nlook around", "ballroom");
    }

    @Test
    public void will_reach_tower_stairwell() {
        assertLocationReached("yes\nup\nup\nlook around", "tower stairwell");
    }

    @Test
    public void will_reach_tower_chamber() {
        assertLocationReached("yes\nup\nup\nup\nlook around", "tower chamber");
    }

    @Test
    public void will_reach_tower_balcony() {
        assertLocationReached("yes\nup\nup\nup\nout\nlook around", "tower balcony");
    }

    private void assertLocationReached(String input, String expectedDisplayName) {
        GameContext context = playGame(input);
        assertThat(context.getGameOutput(), containsString("your in a " + expectedDisplayName));
    }

    private GameContext playGame(String input) {
        GameContext context = initGame(input);
        LoopingGame<Artifact> loop = context.getLoop();

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
        private final ByteArrayOutputStream stream;
        private final PrintStream printStream;
        private final Game game;
        private final LoopingGame<Artifact> loop;

        public GameContext(String gameInput) {
            this(cleanInput(gameInput), countMatches(cleanInput(gameInput), '\n'));
        }

        private GameContext(String gameInput, int iterations) {
            scanner = new Scanner(new ByteArrayInputStream(gameInput.getBytes()));
            stream = new ByteArrayOutputStream();
            printStream = new PrintStream(stream);
            game = new TheDarkOfDarkness(scanner, printStream);
            loop = new LoopingGame<>(
                    DEFAULT,
                    game,
                    scanner,
                    printStream);
            loop.setWillContinue(new ExecuteXTimes(iterations));
        }

        public LoopingGame<Artifact> getLoop() {
            return loop;
        }

        public Location getCurrentLocation() {
            return getLoop().getCurrentLocation();
        }

        public String getGameOutput() {
            return trim(stream.toString());
        }
    }

    public static String cleanInput(String input) {
        return trim(input) + "\n";
    }
}
