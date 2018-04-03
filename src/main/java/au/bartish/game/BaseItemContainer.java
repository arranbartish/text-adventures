package au.bartish.game;

import au.bartish.game.utilities.ListBuilder;
import au.bartish.game.utilities.StringBuilderListBuilder;

import java.util.ArrayList;
import java.util.Collection;

public class BaseItemContainer implements ItemContainer {
    Collection<String> items = new ArrayList<String>();
    private ListBuilder listBuilder = new StringBuilderListBuilder();

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

    public boolean isEmpty() {
        return itemsCount() < 1;
    }

    public String listItems() {
        return listBuilder.listItems(items);
    }
}
