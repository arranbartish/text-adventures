package au.bartish.game.firstfloor;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.DISPLAY_GUN;
import static au.bartish.game.Artifact.SILVER_CUTLERY;

public class Ballroom extends BaseItemContainer implements Location {

    private final House house;

    public Ballroom(House house) {
        this.house = house;
        this.put(DISPLAY_GUN.get());
        this.put(SILVER_CUTLERY.get());
    }

    @Override
    public String getStory() {
        return "You're in the grand ballroom. It has " +
                ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
                "\n\nYou can exit to the landing";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("exit") || action.equalsIgnoreCase("leave")) {
            return house.get("landing");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "ballroom";
    }
}
