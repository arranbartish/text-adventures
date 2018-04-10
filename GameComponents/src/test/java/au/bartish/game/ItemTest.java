package au.bartish.game;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import static au.bartish.game.Item.create;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ItemTest {

    @Test
    public void will_clone_item() {
        Item item = create("air");
        Item clone = ObjectUtils.clone(item);
        assertThat(clone, is(item));
    }

}