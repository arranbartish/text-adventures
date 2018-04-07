package au.bartish.game.pond;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.World;

public class ForbiddenPond extends BaseItemContainer implements Location {

    private final World world;
    private boolean isFrantic = true;

    public ForbiddenPond(World world) {
        this.world = world;
    }

    public String getStory() {
        return ((isFrantic) ? "You see the mermaid is distressed." : "The mermaid has told you that Rey missing and needs your help.") +
                "\n\nThe pond has blue water and purple and pink sparkles. " +
                "\nThis is the Forbidden pond ruled by Princess Rey of the Mermaids. You can:" +
                ((isFrantic) ? "\n- Ask what's wrong" : "\n- Ask mermaid for help") +
                "\n- Fly away" +
                "\n- Enter the pond";
    }

    public String getQuestion() {
        return "What would you like to do?";
    }

    public Location doAction(String action) {
        if(action.equalsIgnoreCase("fly away")) {
            System.out.println("The mermaid is shocked to see you lift off the ground and fly away.");
            System.out.println("It's hunting season... too bad. The End.");
            System.exit(0);
        }
        return this;
    }

    public String getDisplayName() {
        return "Forbidden Pond";
    }
}
