package au.bartish.game.chat;

/**
 * Role of a single message in a chat conversation, matching the roles used by
 * the OpenAI Chat Completions API.
 */
public enum ChatRole {

    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String wireName;

    ChatRole(String wireName) {
        this.wireName = wireName;
    }

    public String getWireName() {
        return wireName;
    }
}
