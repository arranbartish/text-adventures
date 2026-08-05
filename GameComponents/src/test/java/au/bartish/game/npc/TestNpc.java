package au.bartish.game.npc;

/**
 * Minimal {@link ConversationalCharacter} used only by tests in this package.
 */
class TestNpc implements ConversationalCharacter {

    private final String name;
    private final String[] aliases;
    private final NpcMemory memory = new NpcMemory();
    private boolean available = true;

    TestNpc(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

    @Override
    public boolean isAvailable(GameStateSnapshot state) {
        return available;
    }

    @Override
    public String getWorldSummary() {
        return "A test world.";
    }

    @Override
    public String getPersonaSection() {
        return "A test persona for " + name + ".";
    }

    @Override
    public String getKnowledgeSection() {
        return "Test knowledge.";
    }

    @Override
    public String getOpeningCue() {
        return "The player approaches " + name + ".";
    }

    @Override
    public String getUnavailableMessage() {
        return name + " is unavailable.";
    }

    @Override
    public String getFarewellMessage() {
        return name + " says farewell.";
    }

    @Override
    public NpcMemory getMemory() {
        return memory;
    }
}
