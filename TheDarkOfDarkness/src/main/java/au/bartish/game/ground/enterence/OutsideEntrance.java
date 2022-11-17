package au.bartish.game.ground.enterence;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.ActionContext;

public class OutsideEntrance extends MansionLocation {

  public OutsideEntrance() {
    super("outsideEntrance");
  }

  public OutsideEntrance(House house) {
    this();
    setHouse(house);
  }

  public String getStory() {
    return "You are in the legend. You see a beautiful village with monsters. There is a " +
      "dark mansion in front of you.";
  }

  public String getQuestion() {
    return "Do you go inside?";
  }

  @Override
  public ActionContext handleAction(ActionContext actionContext) {
    return ActionContext.builderFromContext(actionContext)
      .withNextLocation(doAction(actionContext.getAction()))
      .build();
  }

  public Location doAction(String action) {
    if (action.equalsIgnoreCase("yes")) {
      getHouse().getOut().println("You go inside.");
      return getHouse().get("hallway");
    } else if (action.equalsIgnoreCase("no")) {
      getHouse().getOut().println("You walk away and get eaten by a monster in the village.");
      System.exit(0);
    } else {
      getHouse().getOut().println("You stand there thinking about if you want to go in.");
      return this;
    }
    return null;
  }

  public String getDisplayName() {
    return "front yard of the mansion";
  }
}
