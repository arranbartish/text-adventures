package au.bartish.game.ground.livingroom;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.GameContext;
import au.bartish.game.model.GameContext.ActionContextBuilder;

import static au.bartish.game.Artifact.BEACH_BALL;
import static au.bartish.game.Artifact.RUBBER_DUCK;

public class LivingRoom extends MansionLocation {

  public LivingRoom() {
    super("livingRoom");
    put(RUBBER_DUCK.get());
    put(BEACH_BALL.get());
  }

  public LivingRoom(House house) {
    this();
    setHouse(house);
  }

  @Override
  public GameContext getStory(GameContext gameContext) {
    return GameContext.builderFromContext(gameContext).addMessage(getStory()).build();
  }

  @Override
  public GameContext getQuestion(GameContext gameContext) {
    return GameContext.builderFromContext(gameContext).addMessage(getQuestion()).build();
  }

  public String getStory() {
    return "You're in the living room and it has " +
      ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
      "\n\nIt has two windows and: " +
      "\n - an exit to the backyard" +
      "\n - a door to the west";
  }

  public String getQuestion() {
    return "What would you like to do?";
  }


  @Override
  public GameContext handleAction(GameContext gameContext) {

    ActionContextBuilder actionContextBuilder = GameContext.builderFromContext(gameContext);

    if (gameContext.actionIsOneOf("west")) {
      actionContextBuilder.withNextLocation(getHouse().get("hallway"));
    } else if (gameContext.actionIsOneOf("exit")) {
      actionContextBuilder.withNextLocation(getHouse().get("yard"));
    } else {
      actionContextBuilder.withNextLocation(this);
    }

    return actionContextBuilder
      .build();
  }


  public String getDisplayName() {
    return "living room";
  }
}
