package au.bartish.game.exit;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.model.GameContext;

public class Exit extends BaseItemContainer implements Location {

  @Override
  public GameContext getStory(GameContext gameContext) {
    return GameContext.builderFromContext(gameContext).addMessage(getStory()).build();
  }

  @Override
  public GameContext getQuestion(GameContext gameContext) {
    return GameContext.builderFromContext(gameContext).addMessage(getQuestion()).build();
  }

  @Override

  public String getStory() {
        return getDisplayName();
    }

  @Override

    public String getQuestion() {
        return "Press any key";
    }


  @Override
  public GameContext handleAction(GameContext gameContext) {
      return GameContext.builderFromContext(gameContext).gameOver().build();
  }

  public String getDisplayName() {
        return "The End!";
    }
}
