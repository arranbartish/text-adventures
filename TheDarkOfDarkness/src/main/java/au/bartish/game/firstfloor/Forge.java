package au.bartish.game.firstfloor;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Forge extends BaseItemContainer implements Location {

    private final House house;

    public Forge(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "The small forge glows with embers, ready to melt silver.\n\n" +
                "You can go back to the study";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("back")) {
            return house.get("study");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "forge";
    }
}
