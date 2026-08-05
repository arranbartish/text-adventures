package au.bartish.game.npc;

import au.bartish.game.chat.ChatClient;
import au.bartish.game.chat.ChatConfiguration;
import au.bartish.game.chat.ChatException;
import au.bartish.game.chat.ChatMessage;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * One run of a conversation with a {@link ConversationalCharacter}. Created
 * fresh for every "talk to" and discarded on exit, so a session owns nothing
 * that must survive it - only {@link NpcMemory}, which the NPC itself owns,
 * survives across sessions. This is what makes leave/re-enter a clean restart:
 * there is no shared mutable conversation state anywhere else.
 */
public class ConversationSession {

    /**
     * Printed when a reply fails after the opening exchange has already
     * succeeded. Deliberately generic and not NPC-specific, matching the design
     * doc's "HTTP error, timeout, or malformed response mid-session" handling.
     */
    static final String MID_SESSION_FAILURE_MESSAGE =
            "Their voice fades into silence. They have nothing more to say for now.";

    public enum EndReason {
        LEFT,
        TURN_CAP_REACHED,
        CHAT_FAILED
    }

    private final ChatClient chatClient;
    private final ChatConfiguration configuration;
    private final Scanner scanner;
    private final PrintStream out;
    private final List<ChatMessage> history = new ArrayList<>();

    private EndReason endReason;

    public ConversationSession(ChatClient chatClient, ChatConfiguration configuration,
                                Scanner scanner, PrintStream out) {
        this.chatClient = chatClient;
        this.configuration = configuration;
        this.scanner = scanner;
        this.out = out;
    }

    /**
     * Runs the full conversation loop: opens with the NPC speaking first, then
     * alternates reading player input and printing replies until the player
     * leaves, the turn cap is reached, or the chat backend fails.
     */
    public void run(ConversationalCharacter npc, GameStateSnapshot state) {
        history.add(ChatMessage.system(PromptBuilder.build(npc, state)));
        history.add(ChatMessage.user(npc.getOpeningCue()));

        if (!sendAndPrintReply()) {
            endReason = EndReason.CHAT_FAILED;
            out.println(TextWrap.wrap(npc.getUnavailableMessage()));
            return;
        }

        int turnsUsed = 1;
        boolean stillTalking = true;
        while (stillTalking) {
            if (turnsUsed >= configuration.getMaxTurns()) {
                endReason = EndReason.TURN_CAP_REACHED;
                out.println(TextWrap.wrap(npc.getFarewellMessage()));
                break;
            }

            String line = scanner.nextLine();
            String trimmed = line == null ? "" : line.trim();

            if (trimmed.isEmpty()) {
                continue;
            }
            if (trimmed.equalsIgnoreCase("leave")) {
                endReason = EndReason.LEFT;
                out.println(TextWrap.wrap(npc.getFarewellMessage()));
                break;
            }

            history.add(ChatMessage.user(trimmed));
            turnsUsed++;
            if (!sendAndPrintReply()) {
                endReason = EndReason.CHAT_FAILED;
                out.println(TextWrap.wrap(MID_SESSION_FAILURE_MESSAGE));
                stillTalking = false;
            }
        }

        npc.getMemory().markSpoken();
        npc.getMemory().markBriefed();
    }

    public EndReason getEndReason() {
        return endReason;
    }

    public List<ChatMessage> getHistory() {
        return List.copyOf(history);
    }

    private boolean sendAndPrintReply() {
        try {
            String reply = chatClient.reply(history);
            out.println(TextWrap.wrap(reply));
            history.add(ChatMessage.assistant(reply));
            return true;
        } catch (ChatException e) {
            return false;
        }
    }
}
