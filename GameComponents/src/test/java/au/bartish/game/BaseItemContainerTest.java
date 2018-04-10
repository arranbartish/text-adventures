package au.bartish.game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class BaseItemContainerTest {

    private final BaseItemContainer container = new TestContainer();
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(baos);
    private final Item SWORD = Item.create("Sword");



    private PrintStream out;

    @Before
    public void setUp() throws Exception {
        out = System.out;
        System.setOut(printStream);
    }

    @Test
    public void container_will_start_empty() {
        assertThat(container.isEmpty(), is(true));
    }

    @Test
    public void container_will_not_be_empty_when_it_has_item() {
        container.put(SWORD);
        assertThat(container.isEmpty(), is(false));
    }

    @Test
    public void container_will_return_a_count_when_item_is_added() {
        container.put(SWORD);
        assertThat(container.itemsCount(), is(1));

    }

    @Test
    public void container_will_return_zero_when_item_is_added_and_removed() {
        container.put(SWORD);
        container.remove(SWORD);
        assertThat(container.itemsCount(), is(0));

    }

    @Test
    public void container_will_return_null_when_items_are_empty() {
        String items = container.listItems();
        assertThat(items, is(isEmptyOrNullString()));
    }

    @Test
    public void container_will_return_list_when_items_are_added() {
        container.put(SWORD);
        String items = container.listItems();
        assertThat(items, containsString("- Sword"));
    }


    @Test
    public void container_will_print_nothing_when_empty() {
        container.view();
        assertThat(baos.toString(), is("nothing\n"));
    }

    @Test
    public void container_will_print_items() {
        container.put(SWORD);
        container.view();
        assertThat(baos.toString(), is("\n- Sword\n"));
    }


    @After
    public void tearDown() throws Exception {
        System.setOut(out);
    }

    private class TestContainer extends BaseItemContainer {

    }
}