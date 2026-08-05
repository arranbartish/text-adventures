package au.bartish.game.firstfloor;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.MIRROR_SHARD;

public class Bathroom extends BaseItemContainer implements Location {

    private final House house;

    public Bathroom(House house) {
        this.house = house;
        this.put(MIRROR_SHARD.get());
    }

    @Override
    public String getStory() {
        return "You're in the bathroom. The mirrors are shattered.\n\n" +
                "The bathroom has " +
                ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
                "\n\nYou can exit to the landing";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("exit") || action.equalsIgnoreCase("leave")) {
            return house.get("landing");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "bathroom";
    }
}
