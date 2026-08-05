package au.bartish.game.firstfloor;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Library extends BaseItemContainer implements Location {

    private final House house;

    public Library(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "You're in the library, lined with tall bookshelves.\n\n" +
                "You can:" +
                "\n - inspect the bookshelf" +
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
        } else if (action.equalsIgnoreCase("shelf") || action.equalsIgnoreCase("bookshelf")) {
            return house.get("bookshelf");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "library";
    }
}
