package au.bartish.game.dungeon;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class DungeonStairway extends BaseItemContainer implements Location {

    private final House house;

    public DungeonStairway(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You descend the ominous dark stairway from the shed into the dungeon depths.\n\n" +
                "You can:" +
                "\n - descend further into the cells" +
                "\n - go back up to the shed";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("back") || action.equalsIgnoreCase("up")) {
            return house.get("shed");
        } else if (action.equalsIgnoreCase("descend")) {
            return house.get("dungeonCells");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "ominous stairway";
    }
}
