package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Item;
import au.bartish.game.Location;
import au.bartish.game.exit.Exit;

public class AnotherSimpleLocation extends BaseItemContainer implements Location {

    public AnotherSimpleLocation() {
        this.put(Item.create("Oven"));
    }

    @Override
    public String getStory() {
        return "Another Story";
    }

    @Override
    public String getQuestion() {
        return "Another question";
    }

    @Override
    public Location doAction(String action) {
        if (action.equalsIgnoreCase("yes")){
            return new Exit();
        }
        return this;
    }

    @Override
    public String getDisplayName() {
        return "Another Simple Location";
    }
}
