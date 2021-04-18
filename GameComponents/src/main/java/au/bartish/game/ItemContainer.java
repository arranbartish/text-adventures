package au.bartish.game;

import java.util.Collection;

public interface ItemContainer {

    void put(Item item);

    Item remove(Item item);

    Collection<Item> removeAllItems();

    int itemsCount();

    void view();

    boolean isEmpty();

    String listItems();

    String getDisplayName();
}
