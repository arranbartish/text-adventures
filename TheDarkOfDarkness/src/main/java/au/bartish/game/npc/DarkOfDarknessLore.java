package au.bartish.game.npc;

/**
 * Shared world lore text used by every conversational NPC in this game, so the
 * SETTING section of every NPC's prompt stays consistent without each persona
 * class duplicating it independently.
 */
final class DarkOfDarknessLore {

    static final String WORLD_SUMMARY =
            "The player has entered a dark mansion whose wizard master dabbled in "
                    + "forbidden magic, lost control of a summoning spell, was killed by what "
                    + "he summoned, and let monsters loose on the village. A portal in the "
                    + "dungeon beneath the shed is still open.";

    private DarkOfDarknessLore() {
    }
}
