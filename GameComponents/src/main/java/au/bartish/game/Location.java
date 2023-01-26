package au.bartish.game;

import au.bartish.game.model.GameContext;

public interface Location extends ItemContainer {

    GameContext getStory(GameContext gameContext);

    GameContext getQuestion(GameContext gameContext);

    @Deprecated
    String getStory();

    @Deprecated
    String getQuestion();

    GameContext handleAction(GameContext gameContext);

    String getDisplayName();
}
