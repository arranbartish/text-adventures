package au.bartish.game.livingroom;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.utilities.ListBuilder;
import au.bartish.game.utilities.StringBuilderListBuilder;

public class LivingRoom extends BaseItemContainer implements Location {

    private final House house;

    public LivingRoom(House house) {
        this.house = house;
        this.put("rubber duck");
        this.put("beach ball");
    }

    public String getStory() {
        return "You're in the living room and it has " +
                ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
                "\n\nIt has two windows and: " +
                "\n - an exit to the backyard" +
                "\n - a door to the west";
    }

    public String getQuestion() {
        return "What would you like to do?";
    }

    public Location doAction(String action) {
        if (action.equalsIgnoreCase("west")) {
            return house.get("hallway");
        }
        return this;
    }

    public String getDisplayName() {
        return "living room";
    }
}
