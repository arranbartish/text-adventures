package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.model.ActionContext;

import static au.bartish.game.basic.SimpleArtifact.SOMETHING;

public class SimpleLocation  extends BaseItemContainer implements Location {

    public SimpleLocation() {
        this.put(SOMETHING.get());
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
    public Location doAction(String action) {
      return handleAction(ActionContext.builder()
        .withAction(action)
        .withCurrentLocation(this)
        .build())
        .getNextLocation();
    }

  @Override
  public ActionContext handleAction(ActionContext actionContext) {
    return ActionContext.builderFromContext(actionContext)
      .withNextLocation(actionContext.actionIsOneOf("yes") ? new AnotherSimpleLocation() : this)
      .build();
  }

  @Override
    public String getDisplayName() {
        return "Simple Location";
    }
}
