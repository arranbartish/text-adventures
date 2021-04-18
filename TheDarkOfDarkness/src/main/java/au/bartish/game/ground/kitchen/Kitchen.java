package au.bartish.game.ground.kitchen;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.*;

public class Kitchen extends BaseItemContainer implements Location {

    private final House house;

    public Kitchen(House house) {
        this.house = house;
        this.put(POT.get());
        this.put(RABBIT_FOOD.get());
        this.put(KNIFE.get());
        this.put(OVEN.get());
    }

    public String getStory() {
        return "You're in the kitchen and the kitchen has" +
        ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
                "\n\nIt is a bright room with 3 windows and as single door east" +
                "\nYou have a sense of creature near by";
    }

    public String getQuestion() {
        return "What would you like to do?";
    }

    public Location doAction(String action) {
        if (action.equalsIgnoreCase("east")){
            return house.get("hallway");
        }
        return this;
    }

    public String getDisplayName() {
        return "Kitchen";
    }
}
