package au.bartish.game.npc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NpcMemoryTest {

    @Test
    public void starts_with_nothing_remembered() {
        NpcMemory memory = new NpcMemory();

        assertThat(memory.hasSpoken()).isFalse();
        assertThat(memory.hasBeenBriefed()).isFalse();
    }

    @Test
    public void marking_flags_makes_them_stick() {
        NpcMemory memory = new NpcMemory();

        memory.markSpoken();
        memory.markBriefed();

        assertThat(memory.hasSpoken()).isTrue();
        assertThat(memory.hasBeenBriefed()).isTrue();
    }
}
