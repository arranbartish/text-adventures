package au.bartish.game.firstfloor;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Study extends BaseItemContainer implements Location {

    private final House house;

    public Study(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You're in the study. It has a sunlight stream, a small forge and a bullet mold.\n\n" +
                "You can:" +
                "\n - use the sunlight stream" +
                "\n - use the forge" +
                "\n - use the bullet mold" +
                "\n - exit to the landing";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("exit") || action.equalsIgnoreCase("leave")) {
            return house.get("landing");
        } else if (action.equalsIgnoreCase("sunlight") || action.equalsIgnoreCase("sunlight stream")) {
            return house.get("sunlightStream");
        } else if (action.equalsIgnoreCase("forge")) {
            return house.get("forge");
        } else if (action.equalsIgnoreCase("mold") || action.equalsIgnoreCase("bullet mold")) {
            return house.get("bulletMold");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "study";
    }
}
