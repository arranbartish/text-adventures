package au.bartish.game;

import au.bartish.game.model.GameContext;

public interface Location extends ItemContainer{

    String getStory();
    String getQuestion();
    GameContext handleAction(GameContext gameContext);
    String getDisplayName();
}
