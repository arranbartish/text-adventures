package au.bartish.game;

import java.io.PrintStream;
import java.util.Scanner;

import static au.bartish.game.Artifact.DEFAULT;

public class TheDarkDuck extends GameTick<Artifact> implements Game {

    private final Scanner scanner;
    private final PrintStream out;
    private HandBag handBag = new HandBag();
    private World world = new World();
    private Location currentLocation = world.get("sky");

    public TheDarkDuck(Scanner scanner, PrintStream out) {
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
        return handBag;
    }

    public static void main( String[] args ) {

        Scanner scanner = new Scanner(System.in);
        TheDarkDuck theDarkDuck = new TheDarkDuck(
                scanner,
                System.out);
        LoopingGame<Artifact> loop = new LoopingGame<>(
                DEFAULT,
                theDarkDuck,
                scanner,
                System.out);

        loop.execute();
    }
}
