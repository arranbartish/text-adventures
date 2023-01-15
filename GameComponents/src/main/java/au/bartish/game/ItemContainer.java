package au.bartish.game;

import java.util.Collection;
import java.util.Optional;

public interface ItemContainer {

    void put(Item item);

    Item remove(Item item);

    Collection<Item> removeAllItems();

    int itemsCount();

    void view();

    boolean isEmpty();

    String listItems();

    String getDisplayName();

    default boolean moveAllItemsTo(ItemContainer target){
      removeAllItems().forEach(target::put);
      return true;
    }

  default boolean moveItemTo(Item item, ItemContainer target) {
    return Optional.of(item).map(this::remove).map(
      thing -> {
        target.put(thing);
        return true;
      }
    ).isPresent();

  }
}
