package au.bartish.game.ground.livingroom;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class LivingRoom extends BaseItemContainer implements Location {

    private final House house;

    public LivingRoom(House house) {
        this.house = house;
    }

    public String getStory() {
        return "You're in the living room and it has " +
                ((this.isEmpty()) ? "nothing in it" : this.listItems()) +
                "\n\nIt has two windows and: " +
                "\n - a toy chest" +
                "\n - an exit to the backyard" +
                "\n - a door to the west";
    }

    public String getQuestion() {
        return "What would you like to do?";
    }

  public Location doAction(String action) {
    if (action.equalsIgnoreCase("west")) {
      return house.get("hallway");
    } else if (action.equalsIgnoreCase("exit")) {
      return house.get("yard");
    } else if (action.equalsIgnoreCase("toy chest") || action.equalsIgnoreCase("chest")) {
      return house.get("toyChest");
    }
    return this;
  }

    public String getDisplayName() {
        return "living room";
    }
}
