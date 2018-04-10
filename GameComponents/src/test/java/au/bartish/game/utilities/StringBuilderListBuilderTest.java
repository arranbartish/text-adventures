package au.bartish.game.utilities;

import au.bartish.game.Listable;
import com.google.common.collect.Lists;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collection;

import static au.bartish.game.Item.create;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertThat;

public class StringBuilderListBuilderTest {

    private ListBuilder builder = new StringBuilderListBuilder();
    private Collection<Listable> items = newArrayList(create("Sword"), create("Wings"));

    private Collection<String> EXPECTED_ITEMS = newArrayList("Sword", "Wings");

    @Test
    public void will_list_items() {
        assertThat(builder.listItems(items), Matchers.stringContainsInOrder(EXPECTED_ITEMS));

    }
}