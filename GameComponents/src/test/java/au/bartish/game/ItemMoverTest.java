package au.bartish.game;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class ItemMoverTest {

    private final ItemContainer source = new TestContainer();
    private final ItemContainer target = new TestContainer();
    private final ItemMover mover = new ItemMover();
    private final Item sword = Item.create("Sword");
    private final Item THIN_AIR = Item.create("Thin Air");


    @Before
    public void setUp() throws Exception {
        source.put(sword);
    }

    @Test
    public void target_will_have_item_after_move() {
        mover.moveItem(sword, source, target);
        assertThat(target.listItems(), containsString("Sword"));
    }

    @Test
    public void source_will_be_empty_after_move() {
        mover.moveItem(sword, source, target);
        assertThat(source.isEmpty(), is(true));
    }

    @Test
    public void mover_will_return_true_when_moved() {
        assertThat(mover.moveItem(sword, source, target), is(true));
    }


    @Test
    public void mover_will_return_false_when_moved() {
        assertThat(mover.moveItem(THIN_AIR, source, target), is(false));
    }


    private class TestContainer extends BaseItemContainer {


        @Override
        public String getDisplayName() {
            return "Test Container";
        }
    }

}