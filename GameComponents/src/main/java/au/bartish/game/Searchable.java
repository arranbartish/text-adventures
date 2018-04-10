package au.bartish.game;

import java.util.Collection;

public interface Searchable<SEARCHABLE> {

    boolean hasTerm(String term);

    SEARCHABLE[] all();

    Collection<SEARCHABLE> find(String term);
}
