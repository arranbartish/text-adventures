package au.bartish.game.pond;

import au.bartish.game.Location;
import au.bartish.game.World;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.*;

public class ForbiddenPondTest {

    private World world = new World();
    private Location pond = world.get("forbiddenPond");

    @Test
    public void will_have_a_story() {
        assertThat(pond.getStory(), not( isEmptyString()));
    }

    @Test
    public void will_have_display_name() {
        assertThat(pond.getDisplayName(), not( isEmptyString()));
    }

    @Test
    public void will_have_question() {
        assertThat(pond.getQuestion(), not( isEmptyString()));
    }

    @Test
    public void will_exist_on_fly_away() {
        assertThat(pond.doAction("fly away"), is(world.get("exit")));
    }

}