package au.bartish.game.livingroom;

import au.bartish.game.Backpack;
import au.bartish.game.House;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class LivingRoomTest {
    private Backpack backpack = new Backpack();
    private LivingRoom livingRoom = new LivingRoom(new House());

    @Test
    public void take_sword_will_fill_backpack() {
        livingRoom.doAction("take rubber duck", backpack);
        Assert.assertThat(backpack.itemsCount(), is(1));
    }

    @Test
    public void take_sword_will_remove_sword_from_living_room() {
        livingRoom.doAction("take rubber duck", backpack);
        Assert.assertThat(livingRoom.getStory(), not(containsString("rubber duck")));
    }
}