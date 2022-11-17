package au.bartish.game;

import au.bartish.game.model.ActionContext;

public interface Location extends ItemContainer{

    String getStory();
    String getQuestion();
    @Deprecated
    Location doAction(String action);

    ActionContext handleAction(ActionContext actionContext);
    String getDisplayName();
}
