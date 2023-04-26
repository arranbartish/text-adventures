package au.bartish.game.ground.yard;

import au.bartish.game.MansionLocation;
import au.bartish.game.model.GameContext;

public class OutsideShed extends MansionLocation {


  public OutsideShed() {
    super("outsideShed");
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
  public String getStory() {
    return "You are in front of the shed and the door has a key holl." +
      "\n\n Now look at what you can do: " +
      "\n - return to house " +
      "\n - see the elf " +
      "\n - open door ";
  }

  @Override
  public String getQuestion() {
    return " what do you want to do now? ";
  }

  @Override
  public GameContext handleAction(GameContext gameContext) {
    if (gameContext.actionIsOneOf("elf", "see elf")) {
      return GameContext.builderFromContext(gameContext).withNextLocation(getHouse().get("UnderTree")).build();
    } else if (gameContext.actionIsOneOf("house","return","return to house")) {
      return GameContext.builderFromContext(gameContext).withNextLocation(getHouse().get("livingRoom")).build();
    } else if (gameContext.actionIsOneOf("door","open","open door")) {
      return GameContext.builderFromContext(gameContext).withNextLocation(getHouse().get("OutsideShed")).build();
    }
    return gameContext;
  }

  @Override
  public String getDisplayName() {
    return "Outside shed";
  }


}
