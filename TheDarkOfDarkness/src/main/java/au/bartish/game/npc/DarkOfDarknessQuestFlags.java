package au.bartish.game.npc;

import au.bartish.game.Artifact;
import au.bartish.game.ItemContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Renders a short, honest quest-progress summary from the player's current
 * inventory, for injection into NPC prompts. Deliberately derived only from
 * what the player is carrying right now - there is no separate quest-state
 * model in this game, so this does not invent flags (like "vampire defeated")
 * that the game itself has no way of tracking.
 */
public final class DarkOfDarknessQuestFlags {

    private DarkOfDarknessQuestFlags() {
    }

    public static String describe(ItemContainer inventory) {
        List<String> flags = new ArrayList<>();
        flags.add(heldFlag(inventory, Artifact.WOODEN_SWORD, "a wooden sword"));
        flags.add(heldFlag(inventory, Artifact.MIRROR_SHARD, "a mirror shard"));
        flags.add(heldFlag(inventory, Artifact.DISPLAY_GUN, "the display gun"));
        flags.add(heldFlag(inventory, Artifact.SILVER_CUTLERY, "silver cutlery"));
        flags.add(heldFlag(inventory, Artifact.TATTERED_GRIMOIRE, "the tattered grimoire"));
        return String.join("; ", flags);
    }

    private static String heldFlag(ItemContainer inventory, Artifact artifact, String label) {
        return isHeld(inventory, artifact) ? ("carrying " + label) : ("does not have " + label + " yet");
    }

    private static boolean isHeld(ItemContainer inventory, Artifact artifact) {
        if (inventory.isEmpty()) {
            return false;
        }
        String listing = inventory.listItems();
        return listing != null
                && listing.toLowerCase().contains(artifact.get().getDisplayName().toLowerCase());
    }
}
