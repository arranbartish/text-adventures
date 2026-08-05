package au.bartish.game.npc;

import au.bartish.game.chat.ChatClient;
import au.bartish.game.chat.ChatConfiguration;

import java.io.PrintStream;
import java.util.Optional;
import java.util.Scanner;

/**
 * Resolves "talk to &lt;npc&gt;" commands against a location's {@link NpcRoster}
 * and runs a fresh {@link ConversationSession} when a match is found. A
 * location calls {@link #handle} first and only falls back to its own action
 * handling when it returns {@code false}.
 */
public class ConversationRunner {

    private static final String TALK_TO_PREFIX = "talk to ";

    private final ChatClient chatClient;
    private final ChatConfiguration configuration;
    private final Scanner scanner;
    private final PrintStream out;

    public ConversationRunner(ChatClient chatClient, ChatConfiguration configuration,
                               Scanner scanner, PrintStream out) {
        this.chatClient = chatClient;
        this.configuration = configuration;
        this.scanner = scanner;
        this.out = out;
    }

    /**
     * @return {@code true} if the action was a "talk to" command matching an
     * available NPC in the roster, in which case a conversation was already
     * run to completion. {@code false} means the caller should handle the
     * action itself.
     */
    public boolean handle(String action, NpcRoster roster, GameStateSnapshot state) {
        if (action == null) {
            return false;
        }
        String trimmed = action.trim();
        if (trimmed.length() <= TALK_TO_PREFIX.length()
                || !trimmed.regionMatches(true, 0, TALK_TO_PREFIX, 0, TALK_TO_PREFIX.length())) {
            return false;
        }

        String alias = trimmed.substring(TALK_TO_PREFIX.length()).trim();
        if (alias.isEmpty()) {
            return false;
        }

        Optional<ConversationalCharacter> npc = roster.resolve(alias, state);
        if (npc.isEmpty()) {
            return false;
        }

        new ConversationSession(chatClient, configuration, scanner, out).run(npc.get(), state);
        return true;
    }
}
