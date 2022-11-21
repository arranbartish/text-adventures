package au.bartish.game.basic;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.ItemFactory;
import au.bartish.game.Location;
import au.bartish.game.exit.Exit;
import au.bartish.game.model.GameContext;

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
  public GameContext handleAction(GameContext gameContext) {
    return GameContext
      .builderFromContext(gameContext)
      .withNextLocation(gameContext.actionIsOneOf("yes")? new Exit(): this)
      .build();
  }

  @Override
    public String getDisplayName() {
        return "Another Simple Location";
    }
}
