package au.bartish.game.exit;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.model.GameContext;

public class Exit extends BaseItemContainer implements Location {

    public String getStory() {
        return getDisplayName();
    }

    public String getQuestion() {
        return "Press any key";
    }

    public Location doAction(String action) {
        handleAction(null);
        return this;
    }

  @Override
  public GameContext handleAction(GameContext gameContext) {
    System.exit(0);
    return null;
  }

  public String getDisplayName() {
        return "The End!";
    }
}
