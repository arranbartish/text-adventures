package au.bartish.game;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collection;

import static au.bartish.game.Action.FLY_AWAY;
import static au.bartish.game.Action.KEEP_FLYING;
import static au.bartish.game.Action.LAND;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.arrayContaining;
import static org.junit.Assert.assertThat;

public class ActionTest {

    @Test
    public void all_gets_all_actions() {
        Action[] all = FLY_AWAY.all();
        assertThat(all, arrayContaining(Action.values()));
    }

    @Test
    public void find_will_find_keep_flying() {
        Collection<Action> set = LAND.find("keep flying");
        assertThat(set, Matchers.contains(KEEP_FLYING));
    }

    @Test
    public void will_get_display_name() {
        assertThat(FLY_AWAY.getDisplayName(), is("fly away"));
    }
}