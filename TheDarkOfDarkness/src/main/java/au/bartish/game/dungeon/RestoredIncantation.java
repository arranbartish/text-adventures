package au.bartish.game.dungeon;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class RestoredIncantation extends BaseItemContainer implements Location {

    private final House house;

    public RestoredIncantation(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "The grimoire is whole again, though the final word is unclear. " +
                "You mumble it and hope for the best.\n\n" +
                "You can recite the incantation";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("recite")) {
            return house.get("victory");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "restored incantation";
    }
}
