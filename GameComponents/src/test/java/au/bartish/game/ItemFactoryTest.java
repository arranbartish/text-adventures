package au.bartish.game;

import au.bartish.game.basic.SimpleArtifact;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class ItemFactoryTest {

    @Test
    public void will_create_item() {
        final ItemFactory itemFactory = new ItemFactory<SimpleArtifact>();
        Item expectedOven = Item.create("oven");
        Item oven = itemFactory.getItem(SimpleArtifact.OVEN);
        Assert.assertThat(oven.getDisplayName(), is(expectedOven.getDisplayName()));
    }
}