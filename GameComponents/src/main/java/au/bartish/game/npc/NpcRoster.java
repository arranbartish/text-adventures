package au.bartish.game.npc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * The conversational NPCs present in one location. A location holds a roster
 * and delegates alias resolution to it, so the location itself stays dumb - it
 * never has to know how many NPCs it contains or what they are called.
 */
public class NpcRoster {

    private final List<ConversationalCharacter> characters;

    public NpcRoster(ConversationalCharacter... characters) {
        this.characters = List.of(characters);
    }

    /**
     * Resolves a player-typed alias (the text after "talk to") against the
     * available NPCs in this roster. An NPC whose {@link ConversationalCharacter#isAvailable}
     * returns false for the given state is treated as not present at all.
     */
    public Optional<ConversationalCharacter> resolve(String alias, GameStateSnapshot state) {
        if (alias == null || alias.isBlank()) {
            return Optional.empty();
        }
        String normalized = alias.trim();
        return characters.stream()
                .filter(character -> character.isAvailable(state))
                .filter(character -> hasMatchingAlias(character, normalized))
                .findFirst();
    }

    private boolean hasMatchingAlias(ConversationalCharacter character, String alias) {
        return Arrays.stream(character.getAliases())
                .anyMatch(candidate -> candidate.equalsIgnoreCase(alias));
    }
}
