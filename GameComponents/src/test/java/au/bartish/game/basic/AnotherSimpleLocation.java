package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Item;
import au.bartish.game.ItemFactory;
import au.bartish.game.Location;
import au.bartish.game.exit.Exit;

import static au.bartish.game.basic.SimpleArtifact.OVEN;

public class AnotherSimpleLocation extends BaseItemContainer implements Location {

    private final ItemFactory itemFactory = new SimpleItemFactory();

    public AnotherSimpleLocation() {
        this.put(OVEN.get());
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
