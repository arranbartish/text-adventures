package au.bartish.game;

import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class NavigationTest {

    @Test
    public void all_will_return_all_items() {
        Navigation[] all = Navigation.GO.all();
        assertThat(all, is(Navigation.values()));
    }

    @Test
    public void find_on_go_will_return_go() {
        Collection<Navigation> itemsWithGo = Navigation.GO.find("go");
        assertThat(itemsWithGo, contains(Navigation.GO));
    }
}