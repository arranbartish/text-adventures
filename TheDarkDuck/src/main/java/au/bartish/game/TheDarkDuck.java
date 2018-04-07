package au.bartish.game;

import java.util.Scanner;

public class TheDarkDuck
{
    public static void main( String[] args ) {
        System.out.println("Welcome to The Dark Duck!");
        System.out.println();

        HandBag handBag = new HandBag();
        World world = new World();
        Location currentLocation = world.get("sky");
        while (true) {
            System.out.println(currentLocation.getStory());
            System.out.println();
            final Scanner keyboard = new Scanner(System.in);
            System.out.println(currentLocation.getQuestion());
            final String response = keyboard.nextLine();
            globalActionHandler(response, currentLocation, handBag);
            currentLocation = currentLocation.doAction(response);
        }
    }

    public static void globalActionHandler(String action, Location location, ItemContainer backpack) {
        final ItemMover itemMover = new ItemMover();
        final String lowerAction = action.toLowerCase();
        if (lowerAction.startsWith("take")) {
            final String item = lowerAction.replaceAll("take ", "");
            if (!itemMover.moveItem(item, location, backpack)){
                System.out.println(item + " is not in the " + location.getDisplayName());
            }
        } else if (action.equalsIgnoreCase("backpack")) {
            System.out.println("your backpack " + ((backpack.isEmpty())? "has nothing in it": "contains:"+backpack.listItems()));
        }
    }

}
