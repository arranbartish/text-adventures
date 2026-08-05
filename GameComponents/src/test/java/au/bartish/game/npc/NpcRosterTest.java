package au.bartish.game.npc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NpcRosterTest {

    private final GameStateSnapshot state = new GameStateSnapshot("nothing", "no progress");

    @Test
    public void resolves_by_exact_alias() {
        TestNpc elf = new TestNpc("Dark Elf", "elf", "dark elf");
        NpcRoster roster = new NpcRoster(elf);

        assertThat(roster.resolve("elf", state)).contains(elf);
    }

    @Test
    public void alias_matching_is_case_and_whitespace_insensitive() {
        TestNpc elf = new TestNpc("Dark Elf", "elf", "dark elf");
        NpcRoster roster = new NpcRoster(elf);

        assertThat(roster.resolve("  ELF  ", state)).contains(elf);
        assertThat(roster.resolve("Dark Elf", state)).contains(elf);
    }

    @Test
    public void unknown_alias_does_not_resolve() {
        NpcRoster roster = new NpcRoster(new TestNpc("Dark Elf", "elf"));

        assertThat(roster.resolve("nobody", state)).isEmpty();
    }

    @Test
    public void unavailable_npc_does_not_resolve() {
        TestNpc spirit = new TestNpc("Vegetable Spirit", "spirit");
        spirit.setAvailable(false);
        NpcRoster roster = new NpcRoster(spirit);

        assertThat(roster.resolve("spirit", state)).isEmpty();
    }

    @Test
    public void blank_or_null_alias_does_not_resolve() {
        NpcRoster roster = new NpcRoster(new TestNpc("Dark Elf", "elf"));

        assertThat(roster.resolve("", state)).isEmpty();
        assertThat(roster.resolve("   ", state)).isEmpty();
        assertThat(roster.resolve(null, state)).isEmpty();
    }

    @Test
    public void two_npcs_each_resolve_by_their_own_alias() {
        TestNpc elf = new TestNpc("Dark Elf", "elf");
        TestNpc spirit = new TestNpc("Vegetable Spirit", "spirit");
        NpcRoster roster = new NpcRoster(elf, spirit);

        assertThat(roster.resolve("elf", state)).contains(elf);
        assertThat(roster.resolve("spirit", state)).contains(spirit);
    }
}
