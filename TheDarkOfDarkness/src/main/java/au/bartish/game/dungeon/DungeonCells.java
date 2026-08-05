package au.bartish.game.dungeon;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.BONES;

public class DungeonCells extends BaseItemContainer implements Location {

    private final House house;

    public DungeonCells(House house) {
        this.house = house;
        this.put(BONES.get());
    }

    @Override
    public String getStory() {
        return "You pass cells filled with the fractured, meat-picked " +
                "bones of the wizard's victims.\n\n" +
                "The cells have " + ((this.isEmpty()) ? "nothing else in them" : this.listItems()) +
                "\n\nYou can:" +
                "\n - pass through to the summoning chamber" +
                "\n - go back to the stairway";
    }

    @Override
    public String getQuestion() {
        return "What would you like to do?";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("back")) {
            return house.get("dungeonStairway");
        } else if (action.equalsIgnoreCase("pass") || action.equalsIgnoreCase("through")) {
            return house.get("summoningChamber");
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "dungeon cells";
    }
}
