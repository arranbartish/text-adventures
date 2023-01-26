package au.bartish.game.ground.kitchen;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.GameContext;
import au.bartish.game.model.GameContext.ActionContextBuilder;

import static au.bartish.game.Artifact.*;

public class Kitchen extends MansionLocation {

  public Kitchen() {
    super("kitchen");
    put(POT.get());
    put(RABBIT_FOOD.get());
    put(KNIFE.get());
    put(OVEN.get());
  }

  public Kitchen(House house) {
    this();
    setHouse(house);
  }

  public String getStory() {
    return "You're in the kitchen and the kitchen has" +
      ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
      "\n\nIt is a bright room with 3 windows and as single door east" +
      "\nYou have a sense of creature near by";
  }

  public String getQuestion() {
    return "What would you like to do?";
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
  public GameContext handleAction(GameContext gameContext) {
    ActionContextBuilder actionContextBuilder = GameContext.builderFromContext(gameContext);

    if (gameContext.actionIsOneOf("east")) {
      actionContextBuilder.withNextLocation(getHouse().get("hallway"));
    }
    return actionContextBuilder.build();
  }


  public String getDisplayName() {
    return "Kitchen";
  }
}
