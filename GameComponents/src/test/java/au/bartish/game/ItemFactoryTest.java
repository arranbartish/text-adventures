package au.bartish.game;

import au.bartish.game.basic.SimpleArtifact;
import au.bartish.game.basic.SimpleItemFactory;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class ItemFactoryTest {

    @Test
    public void will_create_item_from_Artifact() {
        final ItemFactory itemFactory = new SimpleItemFactory();
        Item expectedOven = Item.create("oven");
        Item oven = itemFactory.getItem(SimpleArtifact.OVEN);
        Assert.assertThat(oven.getDisplayName(), is(expectedOven.getDisplayName()));
    }

    @Test
    public void will_create_same_item_from_text() {
        final ItemFactory itemFactory = new SimpleItemFactory();
        Item expectedOven = itemFactory.getItem(SimpleArtifact.OVEN);
        Item oven = itemFactory.getItem("oven");

        Assert.assertThat(oven.getDisplayName(), is(expectedOven.getDisplayName()));
    }

}