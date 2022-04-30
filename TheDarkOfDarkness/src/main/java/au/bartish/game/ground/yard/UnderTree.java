package au.bartish.game.ground.yard;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.APPLE;
import static au.bartish.game.Artifact.SKIPPING_ROPE;

public class UnderTree extends BaseItemContainer implements Location {

  private final House house;

  public UnderTree(House house) {
    this.house = house;
    this.put(APPLE.get());
    this.put(SKIPPING_ROPE.get());
  }
  @Override
  public String getStory() {
    return "The elf continues to mutter himself about a troll." +
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
  public Location doAction(String action) {
    if (action.equalsIgnoreCase("stop")
      || action.equalsIgnoreCase("leave")
      || action.equalsIgnoreCase("bye")
    ) {
      return house.get("livingRoom");
    }
    return this;
  }

  @Override
  public String getDisplayName() {
    return "underTree";
  }
}
