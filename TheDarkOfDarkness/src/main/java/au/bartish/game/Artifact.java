package au.bartish.game;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;

import static au.bartish.game.Item.create;

public enum Artifact implements GameArtifact<Artifact> {
    DEFAULT(create("default"), ""),
    MEDICINE(create("Medicine"), "medicine", "all"),
    WOODEN_SWORD(create("Wooden Sword"), "sword", "all"),
    POT(create("Pot"), "pot", "all"),
    RABBIT_FOOD(create("Rabbit food"), "rabbit food", "rabbit", "food", "all"),
    KNIFE(create("Knife"), "knife", "all"),
    RUBBER_DUCK(create("Rubber duck"), "rubber duck", "rubber", "duck", "all"),
    BEACH_BALL(create("Beach ball"), "beach ball", "beach", "ball", "all"),
    OVEN(create("Oven"), "oven", "all"),
    INVISIBLE_CLOAK(create("Invisible cloak"), "invisible cloak", "invisible", "cloak", "all"),
    SKIPPING_ROPE(create("Skipping Rope"), "rope", "all"),
    APPLE(create("Apple"), "apple", "all")
  ;

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

    public Artifact getDefaultArtifact() {
        return DEFAULT;
    }


    @Override
    public String getDisplayName() {
        return item.getDisplayName();
    }

    @Override
    public Listable listable() {
        return this;
    }
}
