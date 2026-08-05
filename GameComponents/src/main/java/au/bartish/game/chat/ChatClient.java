package au.bartish.game.chat;

import java.util.List;

/**
 * A narrow seam over an LLM chat backend. Production code depends only on this
 * interface, never on a concrete transport, so it can be tested entirely
 * offline with a fake implementation.
 */
public interface ChatClient {

    /**
     * Produces the next assistant reply for the given message history.
     *
     * @throws ChatException if no reply could be produced, for any reason
     */
    String reply(List<ChatMessage> messages) throws ChatException;
}
