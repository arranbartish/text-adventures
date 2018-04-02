package au.bartish.game;

import java.util.ArrayList;
import java.util.Collection;

public class Backpack {
    Collection<String> items = new ArrayList<String>();

    public void put(String item) {
        items.add(item);
    }

    public String remove(String item) {
        return (items.remove(item))? item: null;
    }

    public int itemsCount() {
        return items.size();
    }

    public void view() {
        for (String item : items) {
            System.out.println(item);
        }
    }
}
