package au.bartish.game;

import org.junit.Test;

import java.util.Collection;

import static au.bartish.game.Artifact.*;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

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


}