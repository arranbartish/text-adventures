package au.bartish.game.npc;

/**
 * A talking NPC. Implementations supply persona content only - the shared
 * conversation mechanics (session lifecycle, prompt skeleton, alias
 * resolution) live in {@link ConversationSession}, {@link PromptBuilder} and
 * {@link NpcRoster} and know nothing about any specific character.
 *
 * Adding a new conversational NPC should mean writing one class implementing
 * this interface and registering it in an {@link NpcRoster} - nothing else in
 * this package should need to change.
 */
public interface ConversationalCharacter {

    /** Display name used in the prompt, e.g. "Dark Elf". */
    String getName();

    /** Words the player may use after "talk to", matched case-insensitively. */
    String[] getAliases();

    /** True when this NPC is currently present and willing to talk. */
    boolean isAvailable(GameStateSnapshot state);

    /** One or two sentences of shared world context for the prompt's SETTING section. */
    String getWorldSummary();

    /** Appearance, manner and motivation, for the prompt's WHO YOU ARE section. */
    String getPersonaSection();

    /** What this NPC knows and may hint at, for the prompt's WHAT YOU KNOW section. */
    String getKnowledgeSection();

    /** Synthetic first user turn so the NPC speaks first when a session opens. */
    String getOpeningCue();

    /** Printed when this NPC is present but no reply could be produced at all. */
    String getUnavailableMessage();

    /** Printed when the player leaves, or the session otherwise ends normally. */
    String getFarewellMessage();

    /** This NPC's persistent, cross-session memory. */
    NpcMemory getMemory();
}
