package au.bartish.game.firstfloor;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Landing extends BaseItemContainer implements Location {

    private final House house;

    public Landing(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You're on the first floor landing.\n\n" +
                "You can go:" +
                "\n - west to the library" +
                "\n - north to the study" +
                "\n - to the bathroom" +
                "\n - to the ballroom" +
                "\n - up to the tower" +
                "\n - down to the hallway";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("west")) {
            return house.get("library");
        } else if (action.equalsIgnoreCase("north")) {
            return house.get("study");
        } else if (action.equalsIgnoreCase("bathroom")) {
            return house.get("bathroom");
        } else if (action.equalsIgnoreCase("ballroom")) {
            return house.get("ballroom");
        } else if (action.equalsIgnoreCase("up")) {
            return house.get("towerStairwell");
        } else if (action.equalsIgnoreCase("down")) {
            return house.get("hallway");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "first floor landing";
    }
}
