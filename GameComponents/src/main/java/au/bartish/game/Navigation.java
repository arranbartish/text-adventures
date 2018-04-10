package au.bartish.game;

import java.util.Collection;

public enum Navigation implements Searchable {
    GO("go");

    private final ArrayTermedSearchable<Navigation> searchable;

    Navigation(String... terms) {
        searchable = new ArrayTermedSearchable(terms, this);
    }

    @Override
    public boolean hasTerm(String term) {
        return searchable.hasTerm(term);
    }

    @Override
    public Navigation[] all() {
        return Navigation.values();
    }

    @Override
    public Collection<Navigation> find(String term) {
        return searchable.find(term);
    }
}
