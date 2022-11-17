package au.bartish.game.exit;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.model.ActionContext;

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
  public ActionContext handleAction(ActionContext actionContext) {
    System.exit(0);
    return null;
  }

  public String getDisplayName() {
        return "The End!";
    }
}
