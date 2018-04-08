package au.bartish.game;

import java.util.Collection;

public interface Searchable {

    boolean hasTerm(String term);

    <T extends Searchable> T[] all();

    <T extends Searchable> Collection<T> find(String term);
}
