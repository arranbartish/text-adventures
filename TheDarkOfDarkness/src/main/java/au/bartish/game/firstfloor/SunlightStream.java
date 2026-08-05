package au.bartish.game.firstfloor;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class SunlightStream extends BaseItemContainer implements Location {

    private final House house;

    public SunlightStream(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "A shaft of sunlight streams through the window.\n\n" +
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
        return "sunlight stream";
    }
}
