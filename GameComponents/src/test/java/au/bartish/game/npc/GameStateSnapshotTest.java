package au.bartish.game.npc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameStateSnapshotTest {

    @Test
    public void exposes_inventory_and_quest_flags() {
        GameStateSnapshot state = new GameStateSnapshot("a sword", "vampire defeated");

        assertThat(state.getInventoryDescription()).isEqualTo("a sword");
        assertThat(state.getQuestFlags()).isEqualTo("vampire defeated");
    }
}
