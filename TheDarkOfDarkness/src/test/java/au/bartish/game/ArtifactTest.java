package au.bartish.game;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collection;

import static au.bartish.game.Artifact.DEFAULT;
import static au.bartish.game.Artifact.SWORD;
import static au.bartish.game.Artifact.values;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

public class ArtifactTest {

    @Test
    public void all_gets_all_actions() {
        Artifact[] all = DEFAULT.all();
        assertThat(all, arrayContaining(values()));
    }

    @Test
    public void find_will_find_keep_flying() {
        Collection<Artifact> set = DEFAULT.find("sword");
        assertThat(set, contains(SWORD));
    }

    @Test
    public void will_get_item_from_artifact() {
        Item sword = SWORD.get();
        assertThat(sword, notNullValue());
    }

}