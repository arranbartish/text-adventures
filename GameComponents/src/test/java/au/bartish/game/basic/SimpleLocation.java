package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.exit.Exit;

public class SimpleLocation  extends BaseItemContainer implements Location {

    @Override
    public String getStory() {
        return "A Story";
    }

    @Override
    public String getQuestion() {
        return "A question";
    }

    @Override
    public Location doAction(String action) {
        return (action.equalsIgnoreCase("yes"))
                ? new AnotherSimpleLocation()
                : this;
    }

    @Override
    public String getDisplayName() {
        return "Simple Location";
    }
}
