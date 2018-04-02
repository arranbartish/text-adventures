package au.bartish.game.hallway;

import au.bartish.game.Backpack;
import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.Woredrobe.Wardrobe;

public class Hallway implements Location {

    private final House house;

    public Hallway(House house) {
        this.house = house;
    }

    public String getStory() {
        return "The door to the mansion is locked and the monsters outside wail because they have missed\n" +
                "a meal where you were on the menu. " +
                "\n\n" +
                "You find yourself in a hallway with 3 closed doors in front of you. There in a door in:" +
                "\n- The west" +
                "\n- The north" +
                "\n- The east";
    }

    public String getQuestion() {
        return "Which door do you want to open";
    }

    public Location doAction(String action, Backpack backpack) {
        if (action.equalsIgnoreCase("north")) {
            return house.get("wardrobe");
        } else if (action.equalsIgnoreCase("north")) {

        } else if (action.equalsIgnoreCase("north")) {

        }

        System.out.println("You stand there thinking about which direction to go.");
        return this;
    }
}
