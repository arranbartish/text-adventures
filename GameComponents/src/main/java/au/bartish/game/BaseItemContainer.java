package au.bartish.game;

import au.bartish.game.utilities.ListBuilder;
import au.bartish.game.utilities.StringBuilderListBuilder;

import java.util.ArrayList;
import java.util.Collection;

public class BaseItemContainer implements ItemContainer {
    Collection<Listable> items = new ArrayList<>();
    private ListBuilder listBuilder = new StringBuilderListBuilder();

    public void put(Item item) {
        items.add(item);
    }

    public Item remove(Item item) {
        return (items.remove(item))? item: null;
    }

    public int itemsCount() {
        return items.size();
    }

    public void view() {
        if(isEmpty()){
            System.out.println("nothing");
        } else {
            System.out.println(listItems());
        }
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public String listItems() {
        return listBuilder.listItems(items);
    }
}
