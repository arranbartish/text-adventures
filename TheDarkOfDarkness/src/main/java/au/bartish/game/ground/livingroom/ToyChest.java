package au.bartish.game.ground.livingroom;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.BEACH_BALL;
import static au.bartish.game.Artifact.RUBBER_DUCK;
import static au.bartish.game.Artifact.WOODEN_SWORD;

public class ToyChest extends BaseItemContainer implements Location {

    private final House house;

    public ToyChest(House house) {
        this.house = house;
        this.put(RUBBER_DUCK.get());
        this.put(BEACH_BALL.get());
        this.put(WOODEN_SWORD.get());
    }

    public String getStory() {
        return "You rummage through the toy chest and it has " +
                ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
                "\n\nYou can leave the toy chest";
    }

    public String getQuestion() {
        return "What would you like to do?";
    }

    public Location doAction(String action) {
        if (action.equalsIgnoreCase("leave") || action.equalsIgnoreCase("exit")) {
            return house.get("livingRoom");
        }
        return this;
    }

    public String getDisplayName() {
        return "toy chest";
    }
}
