package au.bartish.game.ground.kitchen;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.ActionContext;

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
  public ActionContext handleAction(ActionContext actionContext) {
    return ActionContext.builderFromContext(actionContext)
      .withNextLocation(doAction(actionContext.getAction()))
      .build();
  }

  public Location doAction(String action) {
    if (action.equalsIgnoreCase("east")) {
      return getHouse().get("hallway");
    }
    return this;
  }

  public String getDisplayName() {
    return "Kitchen";
  }
}
