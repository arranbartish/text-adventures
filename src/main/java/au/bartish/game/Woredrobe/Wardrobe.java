package au.bartish.game.Woredrobe;

import au.bartish.game.Backpack;
import au.bartish.game.House;
import au.bartish.game.Location;

import java.util.ArrayList;
import java.util.Collection;

public class Wardrobe implements Location{

    private Collection<String> items = new ArrayList<String>();
    private final House house;

    public Wardrobe(House house) {
        this.house = house;
        items.add("invisible cloak");
        items.add("sword");
        items.add("medicine");
    }

    private String listItems(){
        StringBuffer stringBuffer = new StringBuffer("");
        if(items.isEmpty()) {
            return null;
        } else {
            for (String item : items) {
                stringBuffer.append("\n- " +  item);
            }
        }
        return stringBuffer.toString();
    }

    public String getStory() {
        return "You find yourself in a wardrobe and the wardrobe has " + ((items.isEmpty()) ? "nothing in it" : listItems()) +
                "\n\n You can exit the wardrobe";
    }

    public String getQuestion() {
        return "What do you want to do?";
    }

    public Location doAction(String action, Backpack backpack) {
        final String lowerAction = action.toLowerCase();
        if (lowerAction.startsWith("take")) {
            final String item = lowerAction.replaceAll("take ", "");
            if (items.remove(item)){
                backpack.put(item);
            } else {
                System.out.println(item + " is not in the wardrobe");
            }
            return this;
        } else if (action.equalsIgnoreCase("exit")) {
            System.out.println("You leave the wardrobe");
            return house.get("hallway");
        }
        System.out.println("You this in the wardrobe thinking. It is a nice wardrobe");
        return this;
    }
}
