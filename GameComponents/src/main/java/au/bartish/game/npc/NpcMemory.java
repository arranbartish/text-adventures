package au.bartish.game.npc;

/**
 * Mutable, per-NPC flags that survive across conversation sessions - unlike a
 * {@link ConversationSession}, which is created fresh and discarded on every
 * "talk to" and "leave". This is deliberately small: v1 only remembers that the
 * player has spoken to this NPC before, and that its opening briefing has
 * already been given, so a returning session's prompt can say "welcome back"
 * instead of repeating itself.
 */
public class NpcMemory {

    private boolean hasSpoken;
    private boolean hasBeenBriefed;

    public boolean hasSpoken() {
        return hasSpoken;
    }

    public boolean hasBeenBriefed() {
        return hasBeenBriefed;
    }

    public void markSpoken() {
        hasSpoken = true;
    }

    public void markBriefed() {
        hasBeenBriefed = true;
    }
}
