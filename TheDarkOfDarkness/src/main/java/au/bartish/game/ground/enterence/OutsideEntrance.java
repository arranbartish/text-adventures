package au.bartish.game.ground.enterence;

import au.bartish.game.House;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.GameContext;
import au.bartish.game.model.GameContext.ActionContextBuilder;
import au.bartish.game.model.Message;

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
  public GameContext handleAction(GameContext gameContext) {
    ActionContextBuilder actionContextBuilder = GameContext.builderFromContext(gameContext);

    if (gameContext.actionIsOneOf("yes")) {
      actionContextBuilder.addMessage(Message.builder()
         .withContent("You go inside.")
         .build())
        .withNextLocation(getHouse().get("hallway"));
    } else if (gameContext.actionIsOneOf("no")) {
        actionContextBuilder.addMessage(Message.builder()
            .withContent("You walk away and get eaten by a monster in the village.")
            .build())
          .gameOver();
    } else {
      actionContextBuilder.addMessage(Message.builder()
          .withContent("You stand there thinking about if you want to go in.")
          .build())
        .withNextLocation(this);
    }

    return actionContextBuilder.build();
  }

  public String getDisplayName() {
    return "front yard of the mansion";
  }
}
