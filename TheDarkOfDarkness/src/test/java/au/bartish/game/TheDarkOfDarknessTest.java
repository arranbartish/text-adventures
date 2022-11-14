package au.bartish.game;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

import static au.bartish.game.Artifact.DEFAULT;
import static au.bartish.game.Artifact.WOODEN_SWORD;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;


public class TheDarkOfDarknessTest {


    @Test
    void will_get_sword()
    {
        GameContext context = playGame("yes\nnorth\ntake sword\nbackpack");
        assertThat(context.getGameOutput())
          .contains("your backpack contains:\n- " + WOODEN_SWORD.getDisplayName());
    }

    @Test
    public void will_put_sword_in_hallway()
    {
        GameContext context = playGame("yes\nnorth\ntake sword\nexit\ndrop sword\nlook around");
        assertThat(context.getGameOutput())
          .contains("your in a hallway and it contains:\n- " + WOODEN_SWORD.getDisplayName());
    }

   @ParameterizedTest(name = "{index} -> {0}")
   @CsvFileSource(resources = "/scenarios.csv")
   void playGames(String game){
      Scenario scenario = loadGame(game);
      GameContext context = playGame(scenario.commands());

     assertThat(context.getGameOutput())
       .contains(scenario.expectations());
   }

  private Scenario loadGame(String game) {
      final String scenarioName = Optional.of(game)
        .map(StringUtils::lowerCase)
        .map(name -> StringUtils.replace(name, " ", "-"))
        .orElseThrow();

    return new Scenario(
      readFile(scenarioName+".txt"),
      readFile(scenarioName+"-expectation.txt")
    );
  }

  private String readFile(String filename) {
      try{
        URI uri = ClassLoader.getSystemResource(filename).toURI();
        return  Files.readString(Path.of(uri));
      } catch (Exception exception) {
        throw new RuntimeException("Failed to read " + filename, exception);
      }
  }

  private record Scenario(String commands, String expectations) {
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

    private static class ExecuteXTimes implements Supplier<Boolean> {

        private int times;

        public ExecuteXTimes(int times) {
            this.times = times;
        }

        @Override
        public Boolean get() {
            return (times-- != 0);
        }
    }

    private static class GameContext {
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
