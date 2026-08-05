package au.bartish.game.firstfloor;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.*;

public class Bookshelf extends BaseItemContainer implements Location {

    private final House house;

    public Bookshelf(House house) {
        this.house = house;
        this.put(BULLET_MAKING_FOR_DUMMIES.get());
        this.put(FIFTY_SHADES_OF_PREY.get());
        this.put(INTERPRETIVE_DANCE_BOOK.get());
        this.put(NECROMANCY_BOOK.get());
        this.put(HAUNTED_CUTLERY_BOOK.get());
        this.put(OVERTHINKING_TRAPS_BOOK.get());
    }

    @Override
    public String getStory() {
        return "The bookshelf has " +
                ((this.isEmpty()) ? "nothing on it" : this.listItems()) +
                "\n\nYou can go back to the library";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("back")) {
            return house.get("library");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "bookshelf";
    }
}
