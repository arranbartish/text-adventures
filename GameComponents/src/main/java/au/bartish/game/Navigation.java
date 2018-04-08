package au.bartish.game;

import java.util.Collection;

public enum Navigation implements Searchable {
    GO("go");

    private final ArrayTermedSearchable searchable;

    Navigation(String... terms) {
        searchable = new ArrayTermedSearchable(terms, this);
    }

    @Override
    public boolean hasTerm(String term) {
        return searchable.hasTerm(term);
    }

    @Override
    public <T extends Searchable> T[] all() {
        return (T[]) Navigation.values();
    }

    @Override
    public <T extends Searchable> Collection<T> find(String term) {
        return searchable.find(term);
    }
}
