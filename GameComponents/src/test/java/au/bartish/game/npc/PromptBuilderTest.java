package au.bartish.game.npc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PromptBuilderTest {

    private final GameStateSnapshot state = new GameStateSnapshot("a wooden sword", "vampire defeated; troll alive");

    @Test
    public void includes_persona_and_knowledge_content() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");

        String prompt = PromptBuilder.build(npc, state);

        assertThat(prompt)
                .contains("Dark Elf")
                .contains(npc.getWorldSummary())
                .contains(npc.getPersonaSection())
                .contains(npc.getKnowledgeSection());
    }

    @Test
    public void includes_current_inventory_and_quest_flags() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");

        String prompt = PromptBuilder.build(npc, state);

        assertThat(prompt).contains("a wooden sword").contains("vampire defeated; troll alive");
    }

    @Test
    public void uses_the_first_visit_memory_line_when_not_yet_briefed() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");

        String prompt = PromptBuilder.build(npc, state);

        assertThat(prompt).contains("first time you have met this player");
        assertThat(prompt).doesNotContain("met this player before");
    }

    @Test
    public void uses_the_returning_visit_memory_line_once_briefed() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");
        npc.getMemory().markBriefed();

        String prompt = PromptBuilder.build(npc, state);

        assertThat(prompt).contains("met this player before");
        assertThat(prompt).doesNotContain("first time you have met this player");
    }

    @Test
    public void instructs_third_person_narration_with_first_person_quoted_speech() {
        TestNpc npc = new TestNpc("Dark Elf", "elf");

        String prompt = PromptBuilder.build(npc, state);

        assertThat(prompt)
                .contains("THIRD PERSON")
                .contains("Never narrate actions as \"I\"")
                .contains("quotation marks");
    }
}
