package au.bartish.game.ground.livingroom;

import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;

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

  public Location doAction(String action) {
    if (action.equalsIgnoreCase("west")) {
      return getHouse().get("hallway");
    } else if (action.equalsIgnoreCase("exit")) {
      return getHouse().get("yard");
    }
    return this;
  }

  public String getDisplayName() {
    return "living room";
  }
}
