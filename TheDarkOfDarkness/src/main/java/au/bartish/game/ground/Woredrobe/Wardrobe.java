package au.bartish.game.ground.Woredrobe;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;
import au.bartish.game.MansionLocation;

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

  public Location doAction(String action) {
    if (action.equalsIgnoreCase("exit")) {
      getHouse().getOut().println("You leave the wardrobe");
      return getHouse().get("hallway");
    }
    getHouse().getOut().println("You sit in the wardrobe thinking. It is a nice wardrobe");
    return this;
  }

  public String getDisplayName() {
    return "wardrobe";
  }
}
