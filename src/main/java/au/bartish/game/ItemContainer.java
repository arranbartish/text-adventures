package au.bartish.game;

public interface ItemContainer {

    void put(String item);

    String remove(String item);

    int itemsCount();

    void view();

    boolean isEmpty();

    String listItems();
}
