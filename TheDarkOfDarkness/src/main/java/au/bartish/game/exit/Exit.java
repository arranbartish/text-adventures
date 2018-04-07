package au.bartish.game.exit;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;

public class Exit extends BaseItemContainer implements Location{

    public String getStory() {
        return "";
    }

    public String getQuestion() {
        return null;
    }

    public Location doAction(String action) {

        return null;
    }

    public String getDisplayName() {
        return null;
    }
}
