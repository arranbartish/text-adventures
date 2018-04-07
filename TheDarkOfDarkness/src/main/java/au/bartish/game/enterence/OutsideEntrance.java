package au.bartish.game.enterence;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class OutsideEntrance extends BaseItemContainer implements Location {

    private final House house;

    public OutsideEntrance(House house) {
        this.house = house;
    }

    public String getStory() {
        return "You are in the legend. You see a beautiful village with monsters. There is a " +
                "dark mansion in front of you.";
    }

    public String getQuestion() {
        return "Do you go inside?";
    }

    public Location doAction(String action) {
        if (action.equalsIgnoreCase("yes")) {
            System.out.println("You go inside.");
            return house.get("hallway");
        } else if (action.equalsIgnoreCase("no")) {
            System.out.println("You walk away and get eaten by a monster in the village.");
            System.exit(0);
        } else {
            System.out.println("You stand there thinking about if you want to go in.");
            return this;
        }
        return null;
    }

    public String getDisplayName() {
        return "front yard of the mansion";
    }
}
