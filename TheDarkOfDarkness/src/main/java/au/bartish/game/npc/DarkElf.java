package au.bartish.game.npc;

/**
 * The Dark Elf, encountered under the apple tree in the yard. Served the
 * mansion's wizard and has failed alone to close the summoning portal.
 */
public class DarkElf implements ConversationalCharacter {

    private static final String[] ALIASES = {"elf", "dark elf", "the elf"};

    private static final String PERSONA =
            "You are gaunt and grey-skinned, with white hair matted with apple "
                    + "blossom. You live under the apple tree and rarely leave its shadow. "
                    + "You are twitchy, cowardly and deeply afraid of the Troll on the first "
                    + "floor. You are sardonic and dryly funny, but you genuinely want the "
                    + "player to succeed. You are a craftsman: you can repair a broken wand if "
                    + "brought all its parts.";

    private static final String KNOWLEDGE =
            "The Vampire in the bathroom is killed with something made of wood, such "
                    + "as the wooden sword. The Troll on the first floor is turned to stone by "
                    + "sunlight redirected with mirror shards. The Werewolf in the tower needs "
                    + "silver bullets. A scrap of paper is missing from the wizard's grimoire, "
                    + "held by a spirit somewhere in the house.";

    private static final String UNAVAILABLE_MESSAGE =
            "The Dark Elf is curled asleep among the roots. He does not stir.";

    private static final String FAREWELL_MESSAGE =
            "The Dark Elf melts back into the shadow of the apple tree.";

    private static final String OPENING_CUE = "The player approaches you under the apple tree.";

    private final NpcMemory memory = new NpcMemory();

    @Override
    public String getName() {
        return "Dark Elf";
    }

    @Override
    public String[] getAliases() {
        return ALIASES;
    }

    @Override
    public boolean isAvailable(GameStateSnapshot state) {
        return true;
    }

    @Override
    public String getWorldSummary() {
        return DarkOfDarknessLore.WORLD_SUMMARY;
    }

    @Override
    public String getPersonaSection() {
        return PERSONA;
    }

    @Override
    public String getKnowledgeSection() {
        return KNOWLEDGE;
    }

    @Override
    public String getOpeningCue() {
        return OPENING_CUE;
    }

    @Override
    public String getUnavailableMessage() {
        return UNAVAILABLE_MESSAGE;
    }

    @Override
    public String getFarewellMessage() {
        return FAREWELL_MESSAGE;
    }

    @Override
    public NpcMemory getMemory() {
        return memory;
    }
}
