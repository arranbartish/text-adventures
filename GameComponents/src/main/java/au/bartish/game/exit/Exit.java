package au.bartish.game.exit;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;

public class Exit extends BaseItemContainer implements Location {

    public String getStory() {
        return getDisplayName();
    }

    public String getQuestion() {
        return "Press any key";
    }

    public Location doAction(String action) {
        System.exit(0);
        return this;
    }

    public String getDisplayName() {
        return "The End!";
    }
}
