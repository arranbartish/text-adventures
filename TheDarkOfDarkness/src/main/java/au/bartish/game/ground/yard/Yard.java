package au.bartish.game.ground.yard;

import au.bartish.game.House;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.GameContext;
import au.bartish.game.model.GameContext.ActionContextBuilder;

import static au.bartish.game.Artifact.*;

public class Yard extends MansionLocation {

  public Yard(){
    super("yard");
    this.put(APPLE.get());
    this.put(SKIPPING_ROPE.get());
  }

  public Yard(House house) {
    this();
    setHouse(house);
  }

  @Override
  public String getStory() {
    return "You enter the yard and feel someone watching you.\n\n" +
      "There is a Dark Elf regarding you from the middle of the yard. " +
      "He is muttering to himself about a Troll.\n\n" +
      "The yard has \n" +
      ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
      "\n\nFrom the yard you can: " +
      "\n - Enter shed" +
      "\n - Return to house"+
      "\n - see the elf";
  }

  @Override
  public String getQuestion() {
    return "What would you like to do?";
  }

  @Override
  public GameContext handleAction(GameContext gameContext) {
    ActionContextBuilder actionContextBuilder = GameContext.builderFromContext(gameContext);
    if (gameContext.actionIsOneOf("return", "house" )) {
      actionContextBuilder.withNextLocation(getHouse().get("livingRoom"));
    } else if (gameContext.actionIsOneOf("elf") ) {
      actionContextBuilder.withNextLocation(getHouse().get("tree"));
    } else if (gameContext.actionIsOneOf("shed", "enter")) {
      actionContextBuilder.withNextLocation(this);
    } else {
      actionContextBuilder.withNextLocation(this);
    }
    return actionContextBuilder.build();
  }

  @Override
  public String getDisplayName() {
    return "yard";
  }
}
