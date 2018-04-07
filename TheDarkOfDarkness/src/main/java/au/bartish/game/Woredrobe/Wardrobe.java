package au.bartish.game.Woredrobe;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.House;
import au.bartish.game.Location;

public class Wardrobe extends BaseItemContainer implements Location {


    private final House house;

    public Wardrobe(House house) {
        this.house = house;
        this.put("invisible cloak");
        this.put("sword");
        this.put("medicine");
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
            System.out.println("You leave the wardrobe");
            return house.get("hallway");
        }
        System.out.println("You sit in the wardrobe thinking. It is a nice wardrobe");
        return this;
    }

    public String getDisplayName() {
        return "wardrobe";
    }
}
