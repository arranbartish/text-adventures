package au.bartish.game;

import au.bartish.game.utilities.ClassLoaderPath;
import au.bartish.game.utilities.TextProvider;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

import static au.bartish.game.Artifact.DEFAULT;

public class TheDarkOfDarkness extends GameTick<Artifact> implements Game {

    private final Scanner scanner;
    private final PrintStream out;
    private final House house;
    private final Backpack backpack = new Backpack();

    private final TextProvider<String, Map<String, Object>> textProvider;
    private Location currentLocation;

    public TheDarkOfDarkness(Scanner scanner, PrintStream out) {
        super(DEFAULT, scanner, out);
        this.scanner = scanner;
        this.out = out;
        this.textProvider = new FreemarkerTextProvider();
        house = new House(this.out);
        currentLocation = house.get("outsideEntrance");
    }

  public TextProvider<String, Map<String, Object>> getTextProvider() {
    return textProvider;
  }

  public void welcome() {
      out.println(textProvider.resolveText("welcome"));
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
