package au.bartish.game;

import au.bartish.game.Woredrobe.Wardrobe;
import au.bartish.game.enterence.OutsideEntrance;
import au.bartish.game.hallway.Hallway;
import au.bartish.game.livingroom.LivingRoom;

import java.util.HashMap;
import java.util.Map;

public class House {

    private Map<String, Location> locations = new HashMap<String, Location>();

    public House() {
        locations.put("hallway", new Hallway(this));
        locations.put("wardrobe", new Wardrobe(this));
        locations.put("outsideEntrance", new OutsideEntrance(this));
        locations.put("livingRoom", new LivingRoom(this));
    }

    public Location get(String location) {
        return locations.get(location);
    }
}
