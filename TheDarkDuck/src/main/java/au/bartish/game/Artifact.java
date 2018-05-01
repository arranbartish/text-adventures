package au.bartish.game;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;

import static au.bartish.game.Item.create;

public enum Artifact implements GameArtifact<Artifact> {
    DEFAULT(create("default"), ""),
    MEDICINE(create("Medicine"), "medicine"),
    SWORD(create("Sword"), "sword");

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

    @Override
    public Artifact getDefaultArtifact() {
        return DEFAULT;
    }

    @Override
    public String getDisplayName() {
        return item.getDisplayName();
    }

    @Override
    public Listable listable() {
        return item;
    }
}
