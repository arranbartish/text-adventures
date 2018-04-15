package au.bartish.game.basic;

import au.bartish.game.*;

import java.io.PrintStream;
import java.util.Scanner;

import static au.bartish.game.basic.SimpleArtifact.DEFAULT;
import static au.bartish.game.basic.SimpleArtifact.SOMETHING;

public class SimpleGame extends GameTick<SimpleArtifact> implements Game {

    private final PrintStream out;
    private final Inventory sack = new Sack();
    private Location currentLocation = new SimpleLocation();

    public SimpleGame(Scanner scanner,
                      PrintStream out) {
        super(DEFAULT, scanner, out);
        this.out = out;
        currentLocation.put(SOMETHING.get());
    }

    @Override
    public void welcome() {
        out.println("A Welcome");
    }

    @Override
    public Location getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void updateLocation(Location newLocation) {
        currentLocation = newLocation;
    }

    @Override
    public Inventory getInventory() {
        return sack;
    }
}
