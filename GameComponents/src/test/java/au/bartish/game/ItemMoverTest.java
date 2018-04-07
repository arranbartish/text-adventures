package au.bartish.game;

import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class ItemMoverTest {

    private final ItemContainer source = new TestContainer();
    private final ItemContainer target = new TestContainer();
    private final ItemMover mover = new ItemMover();

    @Before
    public void setUp() throws Exception {
        source.put("item");
    }

    @Test
    public void target_will_have_item_after_move() {
        mover.moveItem("item", source, target);
        assertThat(target.listItems(), containsString("item"));
    }

    @Test
    public void source_will_be_empty_after_move() {
        mover.moveItem("item", source, target);
        assertThat(source.isEmpty(), is(true));
    }

    @Test
    public void mover_will_return_true_when_moved() {
        assertThat(mover.moveItem("item", source, target), is(true));
    }


    @Test
    public void mover_will_return_false_when_moved() {
        assertThat(mover.moveItem("no_item", source, target), is(false));
    }


    private class TestContainer extends BaseItemContainer {

    }
}