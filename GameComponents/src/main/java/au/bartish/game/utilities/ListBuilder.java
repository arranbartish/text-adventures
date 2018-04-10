package au.bartish.game.utilities;

import au.bartish.game.Listable;

import java.util.Collection;

public interface ListBuilder {

    String listItems(Collection<Listable> items);
}
