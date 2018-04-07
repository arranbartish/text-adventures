package au.bartish.game.kitchen;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Kitchen extends BaseItemContainer implements Location {

    private final House house;

    public Kitchen(House house) {
        this.house = house;
        this.put("pot");
        this.put("rabbit food");
        this.put("knife");
        this.put("oven");
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
