package au.bartish.game.dungeon;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Victory extends BaseItemContainer implements Location {

    private final House house;

    public Victory(House house) {
        this.house = house;
    }

    @Override
    public String getStory() {
        return "The portal closes, but the magic recoil drags you through it.\n\n" +
                "You reappear before a demonic castle lined with stone gargoyles.\n" +
                "...did that one just move?\n\n" +
                "To be continued...";
    }

    @Override
    public String getQuestion() {
        return "Press any key";
    }

    @Override
    public Location doAction(String action) {
        System.exit(0);
        return this;
    }

    @Override
    public String getDisplayName() {
        return "demonic castle";
    }
}
