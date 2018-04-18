package au.bartish.game.basic;

import au.bartish.game.ArrayTermedSearchable;
import au.bartish.game.GameArtifact;
import au.bartish.game.Item;

import java.util.Collection;

public enum SimpleArtifact implements GameArtifact<SimpleArtifact> {

    DEFAULT(""),
    SOMETHING("something"),
    WATER("water"),
    BERRIES("berries"),
    CRANBERRIES("cranberries");



    private Item item = Item.create("something");
    private final String[] terms;
    private final ArrayTermedSearchable<SimpleArtifact> arrayTermedSearchable;

    SimpleArtifact(String... terms) {
        this.terms = terms;
        arrayTermedSearchable = new ArrayTermedSearchable<>(terms, this);
    }

    @Override
    public SimpleArtifact getDefaultArtifact() {
        return DEFAULT;
    }

    @Override
    public Item get() {
        try {
            return (Item) item.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Could not clone item");
        }
    }

    @Override
    public boolean hasTerm(String term) {
        return arrayTermedSearchable.hasTerm(term);
    }

    @Override
    public SimpleArtifact[] all() {
        return SimpleArtifact.values();
    }

    @Override
    public Collection<SimpleArtifact> find(String term) {
        return arrayTermedSearchable.find(term);
    }
}
