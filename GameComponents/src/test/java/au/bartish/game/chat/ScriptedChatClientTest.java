package au.bartish.game.chat;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScriptedChatClientTest {

    @Test
    public void returns_scripted_replies_in_order() throws ChatException {
        ScriptedChatClient client = new ScriptedChatClient("first reply", "second reply");

        assertThat(client.reply(List.of(ChatMessage.user("hi")))).isEqualTo("first reply");
        assertThat(client.reply(List.of(ChatMessage.user("again")))).isEqualTo("second reply");
    }

    @Test
    public void throws_once_the_script_is_exhausted() throws ChatException {
        ScriptedChatClient client = new ScriptedChatClient("only reply");
        client.reply(List.of(ChatMessage.user("hi")));

        assertThatThrownBy(() -> client.reply(List.of(ChatMessage.user("again"))))
                .isInstanceOf(ChatException.class);
    }

    @Test
    public void records_every_request_it_received() throws ChatException {
        ScriptedChatClient client = new ScriptedChatClient("a reply");
        List<ChatMessage> request = List.of(ChatMessage.system("persona"), ChatMessage.user("hi"));

        client.reply(request);

        assertThat(client.getRequestsReceived()).containsExactly(request);
        assertThat(client.callCount()).isEqualTo(1);
    }
}
