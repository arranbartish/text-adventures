package au.bartish.game;

import au.bartish.game.pond.ForbiddenPond;
import au.bartish.game.sky.Sky;

import java.util.HashMap;
import java.util.Map;

public class World {

    private Map<String, Location> locations = new HashMap<String, Location>();

    public World() {
        locations.put("sky", new Sky(this));
        locations.put("forbiddenPond", new ForbiddenPond(this));

    }

    public Location get(String location) {
        return locations.get(location);
    }
}
