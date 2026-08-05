package au.bartish.game.chat;

import java.util.Objects;

/**
 * A single, immutable message in a chat conversation.
 */
public final class ChatMessage {

    private final ChatRole role;
    private final String content;

    private ChatMessage(ChatRole role, String content) {
        this.role = Objects.requireNonNull(role, "role");
        this.content = Objects.requireNonNull(content, "content");
    }

    public static ChatMessage system(String content) {
        return new ChatMessage(ChatRole.SYSTEM, content);
    }

    public static ChatMessage user(String content) {
        return new ChatMessage(ChatRole.USER, content);
    }

    public static ChatMessage assistant(String content) {
        return new ChatMessage(ChatRole.ASSISTANT, content);
    }

    public ChatRole getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;
        ChatMessage that = (ChatMessage) o;
        return role == that.role && content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, content);
    }

    @Override
    public String toString() {
        return role.getWireName() + ": " + content;
    }
}
