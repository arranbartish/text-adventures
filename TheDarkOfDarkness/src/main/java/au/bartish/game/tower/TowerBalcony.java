package au.bartish.game.tower;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class TowerBalcony extends BaseItemContainer implements Location {

    private final House house;

    public TowerBalcony(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You step out onto the tower balcony, overlooking the village.\n\n" +
                "You can go back to the tower chamber";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("back") || action.equalsIgnoreCase("return")) {
            return house.get("towerChamber");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "tower balcony";
    }
}
