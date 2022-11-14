package au.bartish.game;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Disabled;
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
import java.util.stream.Stream;

import static au.bartish.game.Artifact.DEFAULT;
import static org.apache.commons.lang3.StringUtils.countMatches;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.assertj.core.api.Assertions.assertThat;


public class TheDarkOfDarknessTest {

  @Test
  void willHaveScenarioForEachFile() {
    String contents = readFile("scenarios/scenarios.csv");
    String[] scenarios = StringUtils.split(contents, "\n");

    String[] gameNames = findGameNames();
    String[] potentialGameNames = convertScenariosToGameNames(scenarios);
    String expectedGames = generateExpectedGames(gameNames, potentialGameNames);
    String actualGames = Stream.of(gameNames)
      .sorted()
      .reduce((left, right) -> left + "\n" + right)
      .orElseThrow();

    assertThat(actualGames).isEqualTo(expectedGames);
  }

  @ParameterizedTest(name = "{index} -> {0}")
  @CsvFileSource(resources = "/scenarios/scenarios.csv")
  void playGames(String game) {
    Assumptions.assumeThat(isNotDisabled(game)).as("%s is disabled", game).isTrue();
    Scenario scenario = loadGame(game);
    GameContext context = playGame(scenario.commands());

    assertThat(context.getGameOutput())
      .contains(scenario.expectations());

  }

  private String generateExpectedGames(String[] currentGameNames, String[] potentialGameNames) {
    return Stream.of(potentialGameNames)
      .map(potentialGame -> gameExistsOutcome(potentialGame, currentGameNames))
      .sorted()
      .reduce((left, right) -> left + "\n" + right)
      .orElseThrow();
  }

  private String gameExistsOutcome(String potentialGame, String[] currentGameNames) {
    return Optional.of(potentialGame)
      .filter(game -> ArrayUtils.contains(currentGameNames, game))
      .or(() -> Optional.of(potentialGame)
        .map(game -> StringUtils.replace(game, ".txt", "-disabled.txt"))
        .filter(game -> ArrayUtils.contains(currentGameNames, game)))
      .orElseGet(() -> potentialGame + " (in scenarios.csv but missing)");
  }




  private boolean isNotDisabled(String game) {
    String disabledGameName = StringUtils.replace(convertScenarioToGameName(game), ".txt", "-disabled.txt");
    String[] gameNames = findGameNames();
    return !ArrayUtils.contains(gameNames, disabledGameName);
  }


  private String[] findGameNames() {
    String[] filenames = findFilenames("scenarios");
    return Stream.of(filenames)
      .filter(filename -> !StringUtils.endsWith(filename, "-expectation.txt"))
      .sorted().toArray(String[]::new);
  }

  private String[] convertScenariosToGameNames(String[] scenarioNames) {
    return Stream.of(scenarioNames)
      .map(this::convertScenarioToGameName)
      .sorted()
      .toArray(String[]::new);
  }

  private String convertScenarioToGameName(String scenarioName) {
    return Optional.of(scenarioName)
      .map(StringUtils::lowerCase)
      .map(name -> StringUtils.replace(name, " ", "-"))
      .map(name -> name + ".txt")
      .orElseThrow();
  }

  private Scenario loadGame(String game) {
    final String gameName = convertScenarioToGameName(game);

    return new Scenario(
      readFile("scenarios/" + gameName),
      readFile("scenarios/" + StringUtils.replace(gameName, ".txt", "-expectation.txt"))
    );
  }

  private String readFile(String filename) {
    try {
      URI uri = ClassLoader.getSystemResource(filename).toURI();
      return Files.readString(Path.of(uri));
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read " + filename, exception);
    }
  }

  private String[] findFilenames(String classpathDirectory) {
    try {
      URI uri = ClassLoader.getSystemResource(classpathDirectory).toURI();
      Path root = Path.of(uri);
      try (Stream<Path> listing = Files.list(root)) {
        return listing
          .filter(file -> endsWith(file, ".txt"))
          .map(Path::getFileName)
          .map(Path::toString)
          .sorted()
          .toArray(String[]::new);
      }
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read contents of " + classpathDirectory, exception);
    }
  }

  private boolean endsWith(Path file, String suffix) {
    return Optional.of(file)
      .map(Path::getFileName)
      .map(Path::toString)
      .filter(filename -> StringUtils.endsWith(filename, suffix))
      .isPresent();
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
