package au.bartish.game.exit;

import au.bartish.game.Backpack;
import au.bartish.game.Location;

public class Exit implements Location{

    public String getStory() {
        return "";
    }

    public String getQuestion() {
        return null;
    }

    public Location doAction(String action, Backpack backpack) {

        return null;
    }
}
