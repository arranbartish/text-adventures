package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.exit.Exit;

public class AnotherSimpleLocation extends BaseItemContainer implements Location {

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
        return new Exit();
    }

    @Override
    public String getDisplayName() {
        return "Another Simple Location";
    }
}
