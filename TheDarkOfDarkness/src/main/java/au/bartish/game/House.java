package au.bartish.game;

import au.bartish.game.ground.Woredrobe.Wardrobe;
import au.bartish.game.ground.enterence.OutsideEntrance;
import au.bartish.game.ground.hallway.Hallway;
import au.bartish.game.ground.kitchen.Kitchen;
import au.bartish.game.ground.livingroom.LivingRoom;
import au.bartish.game.ground.yard.Yard;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class House {

    private Map<String, Location> locations = new HashMap<String, Location>();
    private final PrintStream out;

    public House() {
        this(System.out);
    }

    public House(PrintStream out) {
        this.out = out;
        locations.put("hallway", new Hallway(this));
        locations.put("wardrobe", new Wardrobe(this));
        locations.put("outsideEntrance", new OutsideEntrance(this));
        locations.put("livingRoom", new LivingRoom(this));
        locations.put("kitchen", new Kitchen(this));
        locations.put("yard", new Yard(this));
    }

    public Location get(String location) {
        return locations.get(location);
    }

    public PrintStream getOut() {
        return out;
    }
}
