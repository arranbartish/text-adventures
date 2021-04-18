package au.bartish.game.ground.enterence;

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
            house.getOut().println("You go inside.");
            return house.get("hallway");
        } else if (action.equalsIgnoreCase("no")) {
            house.getOut().println("You walk away and get eaten by a monster in the village.");
            System.exit(0);
        } else {
            house.getOut().println("You stand there thinking about if you want to go in.");
            return this;
        }
        return null;
    }

    public String getDisplayName() {
        return "front yard of the mansion";
    }
}
