package au.bartish.game;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;

import static au.bartish.game.Item.create;

public enum Artifact implements GameArtifact<Artifact> {
    DEFAULT(create("default"), ""),
    MEDICINE(create("Medicine"), "medicine", "all"),
    SWORD(create("Sword"), "sword", "all"),
    POT(create("Pot"), "pot", "all"),
    RABBIT_FOOD(create("Rabbit food"), "rabbit food", "rabbit", "food", "all"),
    KNIFE(create("Knife"), "knife", "all"),
    RUBBER_DUCK(create("Rubber duck"), "rubber duck", "rubber", "duck", "all"),
    BEACH_BALL(create("Beach ball"), "beach ball", "beach", "ball", "all"),
    OVEN(create("Oven"), "oven", "all"),
    INVISIBLE_CLOAK(create("Invisible cloak"), "invisible cloak", "invisible", "cloak", "all"),
    SKIPPING_ROPE(create("Skipping Rope"), "rope", "all"),
    APPLE(create("Apple"), "apple", "all"),
    WOODEN_SWORD(create("Wooden sword"), "wooden sword", "wooden", "all"),
    MIRROR_SHARD(create("Mirror shard"), "mirror shard", "mirror", "shard", "all"),
    DISPLAY_GUN(create("Display gun"), "display gun", "gun", "all"),
    SILVER_CUTLERY(create("Silver cutlery"), "silver cutlery", "cutlery", "silver", "all"),
    BONES(create("Bones"), "bones", "all"),
    TATTERED_GRIMOIRE(create("Tattered grimoire"), "tattered grimoire", "grimoire", "all"),
    BULLET_MAKING_FOR_DUMMIES(create("Bullet Making for Dummies"), "bullet making for dummies", "all"),
    FIFTY_SHADES_OF_PREY(create("Fifty Shades of Prey"), "fifty shades of prey", "all"),
    INTERPRETIVE_DANCE_BOOK(create("How to Win Fights with Interpretive Dance"), "how to win fights with interpretive dance", "all"),
    NECROMANCY_BOOK(create("Necromancy for People Who Hate Mornings"), "necromancy for people who hate mornings", "all"),
    HAUNTED_CUTLERY_BOOK(create("101 Uses for Haunted Cutlery"), "101 uses for haunted cutlery", "all"),
    OVERTHINKING_TRAPS_BOOK(create("The Complete Guide to Overthinking Traps"), "the complete guide to overthinking traps", "all")
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
