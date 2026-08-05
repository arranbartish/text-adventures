package au.bartish.game.chat;

import java.util.List;

/**
 * A {@link ChatClient} used whenever no real backend is configured (for
 * example, no API key present). Always fails, so "no key configured" and "the
 * API broke" are the same code path in every caller and get exercised by the
 * same tests.
 */
public class SilentChatClient implements ChatClient {

    @Override
    public String reply(List<ChatMessage> messages) throws ChatException {
        throw new ChatException("No chat backend is configured.");
    }
}
