package au.bartish.game;

import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Supplier;

import static au.bartish.game.Artifact.DEFAULT;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.Assert.*;

public class TheDarkOfDarknessTest {


    @Test
    public void will_get_sword()
    {
        GameContext context = playGame("yes\nnorth\ntake sword\nbackpack");
        assertThat(context.getGameOutput(),
                endsWith("your backpack contains:\n- Sword"));
    }

    @Test
    public void will_put_sword_in_hallway()
    {
        GameContext context = playGame("yes\nnorth\ntake sword\nexit\ndrop sword\nlook around");
        assertThat(context.getGameOutput(),
                endsWith("your in a hallway and it contains:\n- Sword"));
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
        private final OutputStream stream;
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