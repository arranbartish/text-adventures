package au.bartish.game;

import au.bartish.game.ground.Woredrobe.Wardrobe;
import au.bartish.game.ground.enterence.OutsideEntrance;
import au.bartish.game.ground.hallway.Hallway;
import au.bartish.game.ground.kitchen.Kitchen;
import au.bartish.game.ground.livingroom.LivingRoom;
import au.bartish.game.ground.yard.OutsideShed;
import au.bartish.game.ground.yard.UnderTree;
import au.bartish.game.ground.yard.Yard;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class House {

    private Map<String, MansionLocation> locations = new HashMap<>();
    private final PrintStream out;

    public House() {
        this(System.out);
    }

    public House(PrintStream out) {
      this(out, List.of(new Hallway(),
      new Wardrobe(),
      new OutsideEntrance(),
      new LivingRoom(),
      new Kitchen(),
      new Yard(),
      new OutsideShed(),
      new UnderTree()));
    }

  public House(PrintStream out, List<MansionLocation> locations) {
    this.out = out;
    locations.forEach(location -> {
      this.locations.put(location.getKey(), location);
      location.setHouse(this);
    });
    this.locations.put("hallway", new Hallway(this));
    this.locations.put("wardrobe", new Wardrobe(this));
    this.locations.put("outsideEntrance", new OutsideEntrance(this));
    this.locations.put("livingRoom", new LivingRoom(this));
    this.locations.put("kitchen", new Kitchen(this));
    this.locations.put("yard", new Yard(this));
    this.locations.put("tree",new UnderTree(this));
  }

    public Location get(String location) {
        return locations.get(location);
    }

    public PrintStream getOut() {
        return out;
    }
}
