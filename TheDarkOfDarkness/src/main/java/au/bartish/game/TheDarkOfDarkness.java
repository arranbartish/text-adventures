package au.bartish.game;

import java.io.PrintStream;
import java.util.Scanner;

import static au.bartish.game.Artifact.DEFAULT;

public class TheDarkOfDarkness extends GameTick<Artifact> implements Game {

    private final Scanner scanner;
    private final PrintStream out;
    private Backpack backpack = new Backpack();
    private House house = new House();
    private Location currentLocation = house.get("outsideEntrance");

    public TheDarkOfDarkness(Scanner scanner, PrintStream out) {
        super(DEFAULT, scanner, out);
        this.scanner = scanner;
        this.out = out;
    }

    public void welcome() {
        out.println("Welcome to the Dark of Darkness!\n");
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
        return backpack;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TheDarkOfDarkness theDarkOfDarkness = new TheDarkOfDarkness(
                scanner,
                System.out);
        LoopingGame<Artifact> loop = new LoopingGame<>(
                DEFAULT,
                theDarkOfDarkness,
                scanner,
                System.out);

        loop.execute();
    }
}
