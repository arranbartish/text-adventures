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
      actionContextBuilder.addMessage("You go inside.")
        .withNextLocation(getHouse().get("hallway"));
    } else if (gameContext.actionIsOneOf("no")) {
        actionContextBuilder.addMessage("You walk away and get eaten by a monster in the village.")
          .gameOver();
    } else {
      actionContextBuilder.addMessage("You stand there thinking about if you want to go in.")
        .withNextLocation(this);
    }

    return actionContextBuilder.build();
  }

  public String getDisplayName() {
    return "front yard of the mansion";
  }
}
