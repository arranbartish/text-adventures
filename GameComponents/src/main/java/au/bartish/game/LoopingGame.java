package au.bartish.game;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Supplier;

public class LoopingGame<ARTIFACT extends GameArtifact> implements Game, GameWrapper {

    private final GameArtifact<ARTIFACT> defaultArtifact;
    private final Game game;
    private final Scanner scanner;
    private final PrintStream out;
    private Supplier<Boolean> willContinue = () -> true;

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
    public void tick() {
       game.tick();
    }

    @Override
    public Location getCurrentLocation() {
        return game.getCurrentLocation();
    }

    @Override
    public void execute() {
        welcome();

        while (willContinue.get()) {
            tick();
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

    public void setWillContinue(Supplier<Boolean> willContinue) {
        this.willContinue = willContinue;
    }
}
