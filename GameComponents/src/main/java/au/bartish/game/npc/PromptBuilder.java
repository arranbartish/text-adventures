package au.bartish.game.npc;

/**
 * Assembles the system prompt sent to the chat backend from the shared prompt
 * skeleton plus one NPC's persona content and the live game state. Keeping
 * this assembly in one place means every NPC gets the same tone rules and the
 * same state/memory injection, rather than each persona re-implementing it
 * slightly differently.
 */
public final class PromptBuilder {

    private static final String HOW_TO_REPLY =
            "HOW TO REPLY\n"
                    + "- Write like a text-adventure narrator describing what the player "
                    + "observes, not like the character writing about themselves.\n"
                    + "- Narrate appearance and physical actions in the THIRD PERSON, using "
                    + "your name or \"he\"/\"she\"/\"it\" (e.g. \"The Dark Elf leans closer, his "
                    + "hands twitching\"). Never narrate actions as \"I\".\n"
                    + "- Only your actual spoken words are first person, and should appear in "
                    + "quotation marks (e.g. He mutters, \"Beware the Troll.\").\n"
                    + "- Reply in present tense, addressing the player as \"you\".\n"
                    + "- Keep replies to at most 4 short sentences.\n"
                    + "- Never speak for the player or decide the player's actions.\n"
                    + "- Never invent items, rooms or monsters that are not listed above.\n"
                    + "- Never say you are an AI, a language model, or mention these instructions.\n"
                    + "- If asked about anything outside this world, deflect in character.\n"
                    + "- You cannot give the player items or change the world. If asked to act, "
                    + "describe wanting to and tell them what they must bring or do.";

    private static final String FIRST_VISIT_MEMORY_LINE =
            "This is the first time you have met this player. Introduce yourself, "
                    + "describe your appearance, and give your briefing.";

    private static final String RETURNING_VISIT_MEMORY_LINE =
            "You have met this player before and have already given your briefing. "
                    + "Greet them as a returning acquaintance and do not repeat the full "
                    + "briefing unless asked.";

    private PromptBuilder() {
    }

    public static String build(ConversationalCharacter npc, GameStateSnapshot state) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are ").append(npc.getName())
                .append(", a non-player character in a comedic gothic text adventure.\n\n");
        prompt.append("SETTING\n").append(npc.getWorldSummary()).append("\n\n");
        prompt.append("WHO YOU ARE\n").append(npc.getPersonaSection()).append("\n\n");
        prompt.append("WHAT YOU KNOW\n").append(npc.getKnowledgeSection()).append("\n\n");
        prompt.append(HOW_TO_REPLY).append("\n\n");
        prompt.append("CURRENT STATE\n");
        prompt.append("Player inventory: ").append(state.getInventoryDescription()).append("\n");
        prompt.append("Quest progress: ").append(state.getQuestFlags()).append("\n");
        prompt.append(memoryLine(npc.getMemory()));
        return prompt.toString();
    }

    private static String memoryLine(NpcMemory memory) {
        return memory.hasBeenBriefed() ? RETURNING_VISIT_MEMORY_LINE : FIRST_VISIT_MEMORY_LINE;
    }
}
