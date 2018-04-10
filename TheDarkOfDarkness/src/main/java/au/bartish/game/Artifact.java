package au.bartish.game;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;

import static au.bartish.game.Item.create;

public enum Artifact implements Searchable<Artifact> {
    DEFAULT(create("default"), ""),
    MEDICINE(create("Medicine"), "medicine"),
    SWORD(create("Sword"), "sword"),
    POT(create("Pot"), "pot"),
    RABBIT_FOOD(create("Rabbit food"), "rabbit food", "rabbit", "food"),
    KNIFE(create("Knife"), "knife"),
    RUBBER_DUCK(create("Rubber duck"), "rubber duck", "rubber", "duck"),
    BEACH_BALL(create("Beach ball"), "beach ball", "beach", "ball"),
    OVEN(create("Oven"), "oven"),
    INVISIBLE_CLOAK(create("Invisible cloak"), "invisible cloak", "invisible", "cloak");

    private final Searchable<Artifact> searchable;
    private final Item item;

    Artifact(Item item, String... terms) {
        this.item = item;
        searchable = new ArrayTermedSearchable(terms, this);
    }

    public boolean hasTerm(String term) {
        return searchable.hasTerm(term);
    }

    public Artifact[] all() {
        return Artifact.values();
    }

    public Collection<Artifact> find(String term) {
        return searchable.find(term);
    }

    public Item get(){
        return ObjectUtils.clone(item);
    }
}
