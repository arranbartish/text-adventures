package au.bartish.game;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Supplier;

import static au.bartish.game.Artifact.DEFAULT;
import static org.junit.Assert.*;

public class TheDarkOfDarknessTest {


    @Test
    public void will_get_sword() {
        String actions = "yes\nnorth\ntake sword";
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


        public GameContext(String gameInput, int iterations) {
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

        public String getGameOutput() {
            return stream.toString();
        }
    }
}