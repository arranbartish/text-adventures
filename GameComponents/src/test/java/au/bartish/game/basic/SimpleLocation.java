package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;

import static au.bartish.game.basic.SimpleArtifact.SOMETHING;

public class SimpleLocation  extends BaseItemContainer implements Location {

    public SimpleLocation() {
        this.put(SOMETHING.get());
    }

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
