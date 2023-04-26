package au.bartish.game.ground.yard;

import au.bartish.game.House;
import au.bartish.game.MansionLocation;
import au.bartish.game.model.GameContext;
import au.bartish.game.model.GameContext.ActionContextBuilder;
import au.bartish.game.model.Message;

public class UnderTree extends MansionLocation {


  private String prefix = "";

  public UnderTree() {
    super("underTree");
  }

  public UnderTree(House house) {
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

  @Override
  public String getStory() {
    return getPrefix() + "The elf continues to mutter himself about a troll." +
      "\nHe shows an interest in you and waits for you to speak." +
       "The elf has in his hands \n" +
      ((this.isEmpty()) ? "nothing" : this.listItems()) +
       "\n\nWith the elf you an ask: " +
       "\n - to stop the conversation" +
       "\n - about the troll" +
       "\n - about the house" +
       "\n - why monsters are in the village";
  }

  @Override
  public String getQuestion() {
    return "What would you like to ask?";
  }

  @Override
  public GameContext handleAction(GameContext gameContext) {
    ActionContextBuilder actionContextBuilder = GameContext.builderFromContext(gameContext);

    if (gameContext.actionIsOneOf("stop","leave","bye")) {
      actionContextBuilder.withNextLocation(getHouse().get("yard"));
    } else if (gameContext.actionIsOneOf("monster", "monsters","village")) {
      actionContextBuilder.addMessage(villageStory())
        .withNextLocation(this);
    } else if (gameContext.actionIsOneOf("troll")) {
      actionContextBuilder.addMessage(trollStory())
        .withNextLocation(this);
    } else if (gameContext.actionIsOneOf("house")) {
      actionContextBuilder.addMessage(trollHouse())
        .withNextLocation(this);
    } else {
      actionContextBuilder
        .withNextLocation(this);
    }

    return
      actionContextBuilder
      .build();
  }

  private Message trollHouse() {
    return Message.builder()

      .build();
  }

  private Message trollStory() {

    return Message.builder().build();
  }

  private String getPrefix() {
    String result = prefix;
    prefix = "";
    return result;
  }

  private String villageStory() {
    return
      """
      On a beautiful day, the sorcerer who owns this mansion
      wonted to grow his power with an army of monstrous
      minions, but the spell went wrong and his monsters went
      wild and took the village and his life.
      
      """;
  }

  @Override
  public String getDisplayName() {
    return "underTree";
  }
}
