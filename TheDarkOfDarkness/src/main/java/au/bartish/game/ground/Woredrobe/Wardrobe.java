package au.bartish.game.ground.Woredrobe;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.GameContext;
import au.bartish.game.model.GameContext.ActionContextBuilder;
import au.bartish.game.model.Message;

import static au.bartish.game.Artifact.*;

public class Wardrobe extends MansionLocation {

  public Wardrobe() {
    super("wardrobe");
    put(INVISIBLE_CLOAK.get());
    put(WOODEN_SWORD.get());
    put(MEDICINE.get());
  }

  public Wardrobe(House house) {
    this();
    setHouse(house);
  }

  public String getStory() {
    return "You find yourself in a wardrobe and the wardrobe has " +
      ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
      "\n\n You can exit the wardrobe";
  }

  public String getQuestion() {
    return "What do you want to do?";
  }

  @Override
  public GameContext handleAction(GameContext gameContext) {
    ActionContextBuilder actionContextBuilder = GameContext.builderFromContext(gameContext);

    if (gameContext.actionIsOneOf("exit")) {
      actionContextBuilder.withNextLocation(getHouse().get("hallway"))
          .addMessage(Message.builder().withContent("You leave the wardrobe").build());
    } else {
      actionContextBuilder.withNextLocation(this)
        .addMessage(Message.builder().withContent("You sit in the wardrobe thinking. It is a nice wardrobe").build());
    }

    return actionContextBuilder.build();
  }


  public String getDisplayName() {
    return "wardrobe";
  }
}
