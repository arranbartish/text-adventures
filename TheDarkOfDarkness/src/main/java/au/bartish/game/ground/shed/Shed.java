package au.bartish.game.ground.shed;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Shed extends BaseItemContainer implements Location {

    private final House house;

    public Shed(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You're in the shed. An ominous dark stairway leads down into the darkness.\n\n" +
                "You can:" +
                "\n - go down the stairway" +
                "\n - leave the shed";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("leave") || action.equalsIgnoreCase("exit")) {
            return house.get("yard");
        } else if (action.equalsIgnoreCase("down")) {
            return house.get("dungeonStairway");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "shed";
    }
}
