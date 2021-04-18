package au.bartish.game;

public class ItemMover {

  public boolean moveAllItems(ItemContainer source, ItemContainer target) {
    source.removeAllItems().forEach(target::put);
    return true;
  }

  public boolean moveItem(Item item, ItemContainer source, ItemContainer target) {
    if (source.remove(item) != null) {
      target.put(item);
      return true;
    }
    return false;
  }
}
