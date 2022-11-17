package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Item;
import au.bartish.game.ItemFactory;
import au.bartish.game.Location;
import au.bartish.game.exit.Exit;
import au.bartish.game.model.ActionContext;
import au.bartish.game.model.ActionContext.ActionContextBuilder;

import static au.bartish.game.basic.SimpleArtifact.OVEN;

public class AnotherSimpleLocation extends BaseItemContainer implements Location {

    private final ItemFactory itemFactory = new SimpleItemFactory();

    public AnotherSimpleLocation() {
        this.put(OVEN.get());
    }

    @Override
    public String getStory() {
        return "Another Story";
    }

    @Override
    public String getQuestion() {
        return "Another question";
    }

    @Override
    public Location doAction(String action) {
      ActionContext context = handleAction(ActionContext
        .builder()
        .withAction(action)
        .withCurrentLocation(this)
        .build());
      return context.getNextLocation();
    }

  @Override
  public ActionContext handleAction(ActionContext actionContext) {
    return ActionContext
      .builderFromContext(actionContext)
      .withNextLocation(actionContext.actionIsOneOf("yes")? new Exit(): this)
      .build();
  }

  @Override
    public String getDisplayName() {
        return "Another Simple Location";
    }
}
