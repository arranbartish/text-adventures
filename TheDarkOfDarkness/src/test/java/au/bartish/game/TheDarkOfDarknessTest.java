package au.bartish.game;

import au.bartish.game.TheDarkOfDarknessTest.ScenarioContext.ScenarioContextBuilder;
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
import java.net.URL;
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
import static org.assertj.core.api.Assertions.in;


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
    ScenarioContext scenarioContext = buildScenarioContext(game);
    Assumptions.assumeThat(scenarioContext.isNotDisabled()).as(scenarioContext.getDisabledReason()).isTrue();

    GameContext context = playGame(scenarioContext.getScenario().commands());

    assertThat(context.getGameOutput())
      .contains(scenarioContext.getScenario().expectations());

  }

  private ScenarioContext buildScenarioContext(final String gameName) {
    ScenarioContextBuilder scenarioContextBuilder = ScenarioContext.builder()
      .withName(gameName)
      .withScenario(loadGame(gameName));

    scenarioContextBuilder = isDisabled(gameName) ? scenarioContextBuilder
      .withDisabledReason(getDisabledReason(gameName)) : scenarioContextBuilder.enabled();
    return scenarioContextBuilder.build();
  }

  private String getDisabledReason(String gameName) {
    String[] gameCommands = loadGameCommands(gameName);
    return Stream.of(gameCommands)
      .map(StringUtils::trimToNull)
      .filter(command -> StringUtils.startsWithIgnoreCase(command, "why:"))
      .map(command -> StringUtils.substring(command, "why:".length(), command.length()))
      .map(StringUtils::trimToNull)
      .findAny()
      .orElseGet(() -> gameName + "is disabled but has no reason. Add a line \"why: a reason\" to scenario file");
  }

  private String[] loadGameCommands(String gameName) {
    String potentialGameName = convertScenarioToGameName(gameName);
    Optional<String> actualGameName = determineRealGameName(potentialGameName);
    return actualGameName.map(this::readFileLines).orElseGet(() -> new String[0]);
  }

  private Optional<String> determineRealGameName(String potentialGameName) {
    return Optional.of(potentialGameName)
      .map(filename -> "scenarios/"+filename)
      .filter(this::fileExists)
      .or(() -> Optional.of(potentialGameName)
        .map(filename -> StringUtils.replace(filename, ".txt", "-disabled.txt"))
        .map(filename -> "scenarios/"+filename)
        .filter(this::fileExists));
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

  private boolean isDisabled(String game) {
    return !isNotDisabled(game);
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

    return ScenarioContext.builder().withName(game).withScenario(new Scenario(
      Stream.of(loadGameCommands(game)).reduce((left, right) -> left + "\n" + right).orElse(null),
      readFile("scenarios/" + StringUtils.replace(gameName, ".txt", "-expectation.txt"))
    )).build().getScenario();
  }

  private boolean fileExists(String filename) {
    try {
      Optional<URL> systemResource = Optional.ofNullable(ClassLoader.getSystemResource(filename));

      if(systemResource.isEmpty()) {
        return false;
      }
      URI uri = systemResource.get().toURI();
      return Files.exists(Path.of(uri));
    } catch (Exception exception) {
      throw new RuntimeException("Failed to check " + filename, exception);
    }
  }

  private String[] readFileLines(String filename) {
    try {
      URI uri = ClassLoader.getSystemResource(filename).toURI();
      return Files.readAllLines(Path.of(uri)).toArray(String[]::new);
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read " + filename, exception);
    }
  }

  private String readFile(String filename) {
    return Stream.of(readFileLines(filename))
      .map(StringUtils::trimToNull)
      .reduce((left, right) -> left + "\n" + right)
      .orElse(null);
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


  public static class ScenarioContext {
    private String name;
    private Scenario scenario;
    private boolean isDisabled;
    private String disabledReason;

    public String getName() {
      return name;
    }

    private void setName(String name) {
      this.name = name;
    }

    public Scenario getScenario() {
      return scenario;
    }

    private void setScenario(Scenario scenario) {
      this.scenario = scenario;
    }

    public boolean isDisabled() {
      return isDisabled;
    }

    public boolean isNotDisabled() {
      return !isDisabled();
    }

    private void setDisabled(boolean disabled) {
      isDisabled = disabled;
    }

    public String getDisabledReason() {
      return disabledReason;
    }

    private void setDisabledReason(String disabledReason) {
      this.disabledReason = disabledReason;
    }

    public static ScenarioContextBuilder builder() {
      return new ScenarioContextBuilder();
    }

    public static class ScenarioContextBuilder implements Builder<ScenarioContext>{

      private ScenarioContext instance = new ScenarioContext();

      public String getName() {
        return instance.getName();
      }

      public ScenarioContextBuilder withName(String name) {
        instance.setName(name);
        return this;
      }

      public ScenarioContextBuilder withScenario(Scenario scenario) {
        instance.setScenario(scenario);
        return this;
      }

      public ScenarioContextBuilder enabled() {
        instance.setDisabledReason(null);
        instance.setDisabled(false);
        return this;
      }
      public ScenarioContextBuilder withDisabledReason(String disabledReason) {
        instance.setDisabledReason(disabledReason);
        instance.setDisabled(true);
        return this;
      }
      @Override
      public ScenarioContext build() {
        final ScenarioContext result = instance;
        instance = null;
        return result;
      }
    }

  }
}
