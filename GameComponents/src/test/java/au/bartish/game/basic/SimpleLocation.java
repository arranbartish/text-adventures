package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.model.GameContext;

import static au.bartish.game.basic.SimpleArtifact.SOMETHING;

public class SimpleLocation  extends BaseItemContainer implements Location {

    public SimpleLocation() {
        this.put(SOMETHING.get());
    }

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
        return "A Story";
    }

    @Override
    public String getQuestion() {
        return "A question";
    }

  @Override
  public GameContext handleAction(GameContext gameContext) {
    GameContext.ActionContextBuilder actionContextBuilder = GameContext.builderFromContext(gameContext);

    if(gameContext.actionIsOneOf("yes")){
      actionContextBuilder.withNextLocation(new AnotherSimpleLocation());
    }

    return actionContextBuilder.build();
  }

  @Override
    public String getDisplayName() {
        return "Simple Location";
    }
}
