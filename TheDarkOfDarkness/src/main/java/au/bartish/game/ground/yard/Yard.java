package au.bartish.game.ground.yard;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

import static au.bartish.game.Artifact.*;
import static au.bartish.game.Artifact.OVEN;

public class Yard extends BaseItemContainer implements Location {

  private final House house;

  public Yard(House house) {
    this.house = house;
    this.put(APPLE.get());
    this.put(SKIPPING_ROPE.get());
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
  public Location doAction(String action) {
    if (action.equalsIgnoreCase("return") || action.equalsIgnoreCase("house") ) {
      return house.get("livingRoom");
    } else if (action.equalsIgnoreCase("elf") ) {
      return house.get("tree");
    }
    return this;
  }

  @Override
  public String getDisplayName() {
    return "yard";
  }
}
