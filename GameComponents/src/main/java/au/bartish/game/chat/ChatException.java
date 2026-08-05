package au.bartish.game.chat;

/**
 * Raised whenever a {@link ChatClient} cannot produce a reply, whether because
 * no backend is configured, a request failed, or a response could not be
 * understood. The message must always be safe to print to the player - it must
 * never contain an API key, request body, or raw transport details that could
 * leak sensitive information.
 */
public class ChatException extends Exception {

    public ChatException(String message) {
        super(message);
    }

    public ChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
