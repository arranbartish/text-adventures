package au.bartish.game.npc;

import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class DarkElfTest {

    private final DarkElf elf = new DarkElf();

    private final GameStateSnapshot state = new GameStateSnapshot("nothing", "no progress");

    @Test
    public void has_the_expected_name_and_aliases() {
        assertThat(elf.getName()).isEqualTo("Dark Elf");
        assertThat(Arrays.asList(elf.getAliases())).containsExactlyInAnyOrder("elf", "dark elf", "the elf");
    }

    @Test
    public void is_always_available() {
        assertThat(elf.isAvailable(state)).isTrue();
    }

    @Test
    public void prompt_sections_are_populated() {
        assertThat(elf.getWorldSummary()).isNotBlank();
        assertThat(elf.getPersonaSection()).isNotBlank();
        assertThat(elf.getKnowledgeSection()).isNotBlank();
        assertThat(elf.getOpeningCue()).isNotBlank();
        assertThat(elf.getUnavailableMessage()).isNotBlank();
        assertThat(elf.getFarewellMessage()).isNotBlank();
    }

    @Test
    public void has_its_own_persistent_memory() {
        assertThat(elf.getMemory()).isNotNull();
        assertThat(elf.getMemory().hasSpoken()).isFalse();
        assertThat(elf.getMemory()).isSameAs(elf.getMemory());
    }
}
