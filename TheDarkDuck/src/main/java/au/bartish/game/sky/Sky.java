package au.bartish.game.sky;

import au.bartish.game.BaseItemContainer;
import au.bartish.game.Location;
import au.bartish.game.World;

public class Sky extends BaseItemContainer implements Location {

    private final World world;

    public Sky(World world) {
        this.world = world;
    }

    public String getStory() {
        return "You are the The Dark Duck and are flying through the sky." +
                "Then you see a mermaid trying to wave you down calling \"HHEEELLP!\"\n\n" +
                "You can:" +
                "\n- land" +
                "\n- keep flying";
    }

    public String getQuestion() {
        return "What you like do?";
    }

    public Location doAction(String action) {
        if (action.equalsIgnoreCase("land")) {
            System.out.println("You swoop from the sky and land beside the frantic mermaid");
            return world.get("forbiddenPond");
        } else if (action.equalsIgnoreCase("keep flying")) {
            System.out.println("You keep flying, but it's hunting season... too bad. The end.");
            System.exit(0);
        }
        System.out.println("You fly in circles thinking about what to do.");
        return this;
    }

    public String getDisplayName() {
        return "the sky";
    }
}
