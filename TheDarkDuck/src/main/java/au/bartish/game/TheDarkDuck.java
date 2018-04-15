package au.bartish.game;

import org.apache.commons.collections.CollectionUtils;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Scanner;

public class TheDarkDuck
{

    private final Scanner scanner;
    private final PrintStream out;
    private HandBag handBag = new HandBag();
    private World world = new World();
    private Location currentLocation = world.get("sky");

    public TheDarkDuck(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

    public void welcome() {
        out.println("Welcome to the Dark of Darkness!\n");
    }

    public void tick() {
        out.println(currentLocation.getStory());
        out.println(currentLocation.getQuestion());
        final String response = scanner.nextLine();
        globalActionHandler(response, currentLocation, handBag);
        currentLocation = currentLocation.doAction(response);
    }


    public static void main( String[] args ) {
        TheDarkDuck theDarkDuck = new TheDarkDuck(
                new Scanner(System.in),
                System.out);
        theDarkDuck.welcome();

        while (true) {
            theDarkDuck.tick();
        }
    }

    public static Artifact whichItem() {
        return Artifact.SWORD;
    }

    public static void globalActionHandler(String action, Location location, ItemContainer backpack) {
        final ItemMover itemMover = new ItemMover();
        final String lowerAction = action.toLowerCase();
        if (lowerAction.startsWith("take")) {
            Collection<Artifact> items = Artifact.DEFAULT.find(lowerAction.replaceAll("take ", ""));
            Item item = ((Artifact) CollectionUtils.get(items, 0)).get();
            if (!itemMover.moveItem(item, location, backpack)){
                System.out.println(item + " is not in the " + location.getDisplayName());
            }
        } else if (action.equalsIgnoreCase("backpack")) {
            System.out.println("your backpack " + ((backpack.isEmpty())? "has nothing in it": "contains:"+backpack.listItems()));
        }
    }

}
