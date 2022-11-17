package au.bartish.game.ground.hallway;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.ActionContext;
import au.bartish.game.model.ActionContext.ActionContextBuilder;
import au.bartish.game.model.Message;

public class Hallway extends MansionLocation {

  public Hallway() {
    super("hallway");
  }

  public Hallway(House house) {
    this();
    setHouse(house);
  }

  public String getStory() {
    return
      """
      The door to the mansion is locked and the monsters outside wail because they have missed
      a meal where you were on the menu.
      
      
      You find yourself in a hallway with 3 closed doors in front of you. There in a door in:
      - The west
      - The north
      - The east""";
  }

  public String getQuestion() {
    return "Which door do you want to open";
  }

  @Override
  public ActionContext handleAction(ActionContext actionContext) {
    ActionContextBuilder actionContextBuilder = ActionContext.builderFromContext(actionContext);
    if (actionContext.actionIsOneOf("north")) {
      actionContextBuilder.withNextLocation(getHouse().get("wardrobe"));
    } else if (actionContext.actionIsOneOf("west")) {
      actionContextBuilder.withNextLocation(getHouse().get("kitchen"));
    } else if (actionContext.actionIsOneOf("east")) {
      actionContextBuilder.withNextLocation(getHouse().get("livingRoom"));
    } else {
      actionContextBuilder.withNextLocation(this)
        .addMessage(Message.builder()
          .withContent("You stand there thinking about which direction to go.")
          .build());
    }
    return actionContextBuilder.build();
  }

  public Location doAction(String action) {
    return handleAction(ActionContext.builder()
      .withCurrentLocation(this)
      .withAction(action)
      .build()).getNextLocation();
  }

  public String getDisplayName() {
    return "hallway";
  }
}
