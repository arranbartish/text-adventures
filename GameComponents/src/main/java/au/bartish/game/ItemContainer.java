package au.bartish.game;

public interface ItemContainer {

    void put(Item item);

    Item remove(Item item);

    int itemsCount();

    void view();

    boolean isEmpty();

    String listItems();

    String getDisplayName();
}
