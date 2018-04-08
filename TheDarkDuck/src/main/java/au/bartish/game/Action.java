package au.bartish.game;

import java.util.Collection;

public enum Action implements Listable, Searchable {
    LAND("land", "land"),
    FLY_AWAY("fly away", "fly", "away", "fly away"),
    KEEP_FLYING("keep flying", "keep flying", "flying");

    private final String displayName;
    private final Searchable searchable;

    Action(String displayName, String... terms) {
        this.displayName = displayName;
        searchable = new ArrayTermedSearchable(terms, this);
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean hasTerm(String term) {
        return searchable.hasTerm(term);
    }

    public <T extends Searchable> T[] all() {
        return (T[]) Action.values();
    }

    public <T extends Searchable> Collection<T> find(String term) {
        return searchable.find(term);
    }
}
