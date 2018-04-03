package au.bartish.game;

public class ItemMover {

    public boolean moveItem(String item, ItemContainer source, ItemContainer target) {
        if (source.remove(item) != null) {
            target.put(item);
            return true;
        }
        return false;
    }
}
