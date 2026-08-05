package au.bartish.game.dungeon;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.TATTERED_GRIMOIRE;

public class SummoningChamber extends BaseItemContainer implements Location {

    private final House house;

    public SummoningChamber(House house) {
        this.house = house;
        this.put(TATTERED_GRIMOIRE.get());
    }

    @Override
    public String getStory() {
        return "The mauled wizard corpse rests on a slab, a " +
                ((this.isEmpty()) ? "nothing" : this.listItems()) + " clutched in its hands.\n\n" +
                "A summoning portal churns in the middle of the chamber.\n\n" +
                "You can:" +
                "\n - restore the grimoire" +
                "\n - go back to the cells";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("back")) {
            return house.get("dungeonCells");
        } else if (action.equalsIgnoreCase("restore")) {
            return house.get("restoredIncantation");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "summoning chamber";
    }
}
