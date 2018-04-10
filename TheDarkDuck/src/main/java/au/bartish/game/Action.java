package au.bartish.game;

import java.util.Collection;

public enum Action implements Listable, Searchable<Action> {
    DEFAULT(""),
    LAND("land", "Land"),
    ASK_WHAT_IS_WRONG("Ask what's wrong", "ask what's wrong", "what's wrong", "wrong"),
    ENTER_THE_POND("Enter the pond", "enter the pond", "enter", "pond"),
    FLY_AWAY("Fly away", "fly", "away", "fly away"),
    KEEP_FLYING("Keep flying", "keep flying", "flying");

    private final String displayName;
    private final Searchable searchable;

    Action(String displayName, String... terms) {
        this.displayName = displayName;
        searchable = new ArrayTermedSearchable(terms, this);
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Listable listable() {
        return this;
    }

    public boolean hasTerm(String term) {
        return searchable.hasTerm(term);
    }

    public Action[] all() {
        return Action.values();
    }

    public Collection<Action> find(String term) {
        return searchable.find(term);
    }
}
