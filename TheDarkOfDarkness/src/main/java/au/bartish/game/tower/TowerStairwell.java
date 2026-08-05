package au.bartish.game.tower;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class TowerStairwell extends BaseItemContainer implements Location {

    private final House house;

    public TowerStairwell(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You're in the tower stairwell.\n\n" +
                "You can:" +
                "\n - go up to the tower chamber" +
                "\n - go down to the landing";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("up")) {
            return house.get("towerChamber");
        } else if (action.equalsIgnoreCase("down")) {
            return house.get("landing");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "tower stairwell";
    }
}
