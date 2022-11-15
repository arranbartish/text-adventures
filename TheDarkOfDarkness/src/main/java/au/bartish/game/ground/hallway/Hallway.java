package au.bartish.game.ground.hallway;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;

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

  public Location doAction(String action) {
    if (action.equalsIgnoreCase("north")) {
      return getHouse().get("wardrobe");
    } else if (action.equalsIgnoreCase("west")) {
      return getHouse().get("kitchen");
    } else if (action.equalsIgnoreCase("east")) {
      return getHouse().get("livingRoom");
    }

    getHouse().getOut().println("You stand there thinking about which direction to go.");
    return this;
  }

  public String getDisplayName() {
    return "hallway";
  }
}
