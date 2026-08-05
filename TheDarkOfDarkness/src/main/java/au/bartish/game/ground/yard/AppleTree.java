package au.bartish.game.ground.yard;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class AppleTree extends BaseItemContainer implements Location {

    private final House house;

    public AppleTree(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You stand beneath the apple tree with the Dark Elf. " +
                "He mutters about a Troll on the first floor.\n\n" +
                "You can leave the apple tree";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("leave") || action.equalsIgnoreCase("exit")) {
            return house.get("yard");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "apple tree";
    }
}
