package au.bartish.game.tower;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class TowerChamber extends BaseItemContainer implements Location {

    private final House house;

    public TowerChamber(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You're in the tower chamber.\n\n" +
                "You can:" +
                "\n - go out to the balcony" +
                "\n - leave to the stairwell";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("out")) {
            return house.get("towerBalcony");
        } else if (action.equalsIgnoreCase("leave") || action.equalsIgnoreCase("exit")) {
            return house.get("towerStairwell");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "tower chamber";
    }
}
