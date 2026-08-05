package au.bartish.game.chat;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatMessageTest {

    @Test
    public void equal_when_role_and_content_match() {
        assertThat(ChatMessage.user("hello")).isEqualTo(ChatMessage.user("hello"));
        assertThat(ChatMessage.user("hello").hashCode()).isEqualTo(ChatMessage.user("hello").hashCode());
    }

    @Test
    public void not_equal_when_role_differs() {
        assertThat(ChatMessage.user("hello")).isNotEqualTo(ChatMessage.assistant("hello"));
    }

    @Test
    public void not_equal_when_content_differs() {
        assertThat(ChatMessage.user("hello")).isNotEqualTo(ChatMessage.user("goodbye"));
    }

    @Test
    public void exposes_role_and_content() {
        ChatMessage message = ChatMessage.system("be an elf");

        assertThat(message.getRole()).isEqualTo(ChatRole.SYSTEM);
        assertThat(message.getContent()).isEqualTo("be an elf");
    }
}
