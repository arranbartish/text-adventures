package au.bartish.game.npc;

/**
 * A read-only snapshot of the parts of the game state an NPC conversation is
 * allowed to see: the player's inventory and a short quest-progress summary.
 * Built once at the start of a session so the conversation sees a consistent
 * world throughout, even if the player's real state could theoretically change
 * concurrently (it cannot today, but this keeps the contract honest).
 */
public final class GameStateSnapshot {

    private final String inventoryDescription;
    private final String questFlags;

    public GameStateSnapshot(String inventoryDescription, String questFlags) {
        this.inventoryDescription = inventoryDescription;
        this.questFlags = questFlags;
    }

    public String getInventoryDescription() {
        return inventoryDescription;
    }

    public String getQuestFlags() {
        return questFlags;
    }
}
