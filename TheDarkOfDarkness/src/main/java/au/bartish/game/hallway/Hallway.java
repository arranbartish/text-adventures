package au.bartish.game.hallway;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Hallway extends BaseItemContainer implements Location {

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

    public Location doAction(String action) {
        if (action.equalsIgnoreCase("north")) {
            return house.get("wardrobe");
        } else if (action.equalsIgnoreCase("west")) {
            return house.get("kitchen");
        } else if (action.equalsIgnoreCase("east")) {
            return house.get("livingRoom");
        }

        System.out.println("You stand there thinking about which direction to go.");
        return this;
    }

    public String getDisplayName() {
        return "hallway";
    }
}
