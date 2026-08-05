package au.bartish.game.chat;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SilentChatClientTest {

    @Test
    public void always_throws_a_chat_exception() {
        SilentChatClient client = new SilentChatClient();

        assertThatThrownBy(() -> client.reply(List.of(ChatMessage.user("hello"))))
                .isInstanceOf(ChatException.class);
    }

    @Test
    public void the_exception_message_does_not_mention_keys_or_credentials() {
        SilentChatClient client = new SilentChatClient();

        assertThatThrownBy(() -> client.reply(List.of(ChatMessage.user("hello"))))
                .extracting(Throwable::getMessage, org.assertj.core.api.InstanceOfAssertFactories.STRING)
                .doesNotContainIgnoringCase("key")
                .doesNotContainIgnoringCase("sk-");
    }
}
