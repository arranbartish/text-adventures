package au.bartish.game.Woredrobe;

import au.bartish.game.Backpack;
import au.bartish.game.House;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

public class WardrobeTest {


    private Backpack backpack = new Backpack();
    private Wardrobe wardrobe = new Wardrobe(new House());

    @Test
    public void take_sword_will_fill_backpack() {
        wardrobe.doAction("take sword", backpack);
        Assert.assertThat(backpack.itemsCount(), is(1));
    }

    @Test
    public void take_sword_will_remove_sword_from_wardrobe() {
        wardrobe.doAction("take sword", backpack);
        Assert.assertThat(wardrobe.getStory(), not(containsString("sword")));
    }
}
