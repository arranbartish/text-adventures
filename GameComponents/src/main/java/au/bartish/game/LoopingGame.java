package au.bartish.game;

import au.bartish.game.model.GameContext;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

public class LoopingGame<ARTIFACT extends GameArtifact> implements Game, GameWrapper {

  private static final Boolean FIRST_TICK = true;
  private final GameArtifact<ARTIFACT> defaultArtifact;
    private final Game game;
    private final Scanner scanner;
    private final PrintStream out;
    private Function<GameContext,Boolean> willContinue = GameContext::isNotGameOver;

    public LoopingGame(GameArtifact<ARTIFACT> defaultArtifact,
                       Game game,
                       Scanner scanner,
                       PrintStream out) {
        this.defaultArtifact = defaultArtifact;
        this.game = game;
        this.scanner = scanner;
        this.out = out;
    }

    @Override
    public void welcome() {
        game.welcome();
    }

    @Override
    public GameContext tick() {
       return game.tick();
    }

    @Override
    public Location getCurrentLocation() {
        return game.getCurrentLocation();
    }

    @Override
    public void execute() {
        welcome();

        GameContext context = null;
        while (Optional.ofNullable(context).map(willContinue).orElse(FIRST_TICK))
        {
          context = tick();
        }
    }

    @Override
    public Inventory getInventory() {
        return game.getInventory();
    }

    @Override
    public void updateLocation(Location newLocation) {
        game.updateLocation(newLocation);
    }

    public void setWillContinue(Function<GameContext,Boolean> willContinue) {
        this.willContinue = willContinue;
    }
}
