package au.bartish.game.Woredrobe;

import au.bartish.game.House;
import au.bartish.game.Location;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class WardrobeTest {

    private House house = new House();
    private Location wardrobe = house.get("wardrobe");

    @Test
    public void story_will_include_sword() {
        assertThat(wardrobe.getStory(), containsString("- Sword"));
    }
}