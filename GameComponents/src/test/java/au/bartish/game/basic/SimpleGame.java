package au.bartish.game.basic;

import au.bartish.game.Game;
import au.bartish.game.GameTick;
import au.bartish.game.Inventory;
import au.bartish.game.Location;

import java.io.PrintStream;
import java.util.Scanner;

import static au.bartish.game.basic.SimpleArtifact.DEFAULT;

public class SimpleGame extends GameTick<SimpleArtifact> implements Game {

    private final PrintStream out;
    private final Inventory sack = new Sack();
    private Location currentLocation = new SimpleLocation();

    public SimpleGame(Scanner scanner,
                      PrintStream out) {
        super(DEFAULT, scanner, out);
        this.out = out;
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
