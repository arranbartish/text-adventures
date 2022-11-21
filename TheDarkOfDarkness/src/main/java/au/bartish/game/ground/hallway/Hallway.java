package au.bartish.game.ground.hallway;

import au.bartish.game.House;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.GameContext;
import au.bartish.game.model.GameContext.ActionContextBuilder;
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
  public GameContext handleAction(GameContext gameContext) {
    ActionContextBuilder actionContextBuilder = GameContext.builderFromContext(gameContext);
    if (gameContext.actionIsOneOf("north")) {
      actionContextBuilder.withNextLocation(getHouse().get("wardrobe"));
    } else if (gameContext.actionIsOneOf("west")) {
      actionContextBuilder.withNextLocation(getHouse().get("kitchen"));
    } else if (gameContext.actionIsOneOf("east")) {
      actionContextBuilder.withNextLocation(getHouse().get("livingRoom"));
    } else {
      actionContextBuilder.withNextLocation(this)
        .addMessage(Message.builder()
          .withContent("You stand there thinking about which direction to go.")
          .build());
    }
    return actionContextBuilder.build();
  }

  public String getDisplayName() {
    return "hallway";
  }
}
