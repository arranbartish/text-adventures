package au.bartish.game;

import au.bartish.game.dungeon.DungeonCells;
import au.bartish.game.dungeon.DungeonStairway;
import au.bartish.game.dungeon.RestoredIncantation;
import au.bartish.game.dungeon.SummoningChamber;
import au.bartish.game.dungeon.Victory;
import au.bartish.game.firstfloor.Ballroom;
import au.bartish.game.firstfloor.Bathroom;
import au.bartish.game.firstfloor.Bookshelf;
import au.bartish.game.firstfloor.BulletMold;
import au.bartish.game.firstfloor.Forge;
import au.bartish.game.firstfloor.Landing;
import au.bartish.game.firstfloor.Library;
import au.bartish.game.firstfloor.Study;
import au.bartish.game.firstfloor.SunlightStream;
import au.bartish.game.ground.Woredrobe.Wardrobe;
import au.bartish.game.ground.enterence.OutsideEntrance;
import au.bartish.game.ground.hallway.Hallway;
import au.bartish.game.ground.kitchen.Kitchen;
import au.bartish.game.ground.livingroom.LivingRoom;
import au.bartish.game.ground.livingroom.ToyChest;
import au.bartish.game.ground.shed.Shed;
import au.bartish.game.ground.yard.AppleTree;
import au.bartish.game.ground.yard.Yard;
import au.bartish.game.tower.TowerBalcony;
import au.bartish.game.tower.TowerChamber;
import au.bartish.game.tower.TowerStairwell;

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
        locations.put("toyChest", new ToyChest(this));
        locations.put("kitchen", new Kitchen(this));
        locations.put("yard", new Yard(this));
        locations.put("appleTree", new AppleTree(this));
        locations.put("shed", new Shed(this));

        locations.put("dungeonStairway", new DungeonStairway(this));
        locations.put("dungeonCells", new DungeonCells(this));
        locations.put("summoningChamber", new SummoningChamber(this));
        locations.put("restoredIncantation", new RestoredIncantation(this));
        locations.put("victory", new Victory(this));

        locations.put("landing", new Landing(this));
        locations.put("library", new Library(this));
        locations.put("bookshelf", new Bookshelf(this));
        locations.put("study", new Study(this));
        locations.put("sunlightStream", new SunlightStream(this));
        locations.put("forge", new Forge(this));
        locations.put("bulletMold", new BulletMold(this));
        locations.put("bathroom", new Bathroom(this));
        locations.put("ballroom", new Ballroom(this));

        locations.put("towerStairwell", new TowerStairwell(this));
        locations.put("towerChamber", new TowerChamber(this));
        locations.put("towerBalcony", new TowerBalcony(this));
    }

    public Location get(String location) {
        return locations.get(location);
    }

    public PrintStream getOut() {
        return out;
    }
}

