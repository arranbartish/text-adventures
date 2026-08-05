package au.bartish.game.chat;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OpenAiResponseParserTest {

    @Test
    public void extracts_the_reply_from_a_normal_response() throws ChatException {
        String json = """
                {
                  "choices": [
                    { "message": { "role": "assistant", "content": "Hello, traveller." } }
                  ]
                }
                """;

        assertThat(OpenAiResponseParser.parseReply(json)).isEqualTo("Hello, traveller.");
    }

    @Test
    public void handles_escaped_quotes_and_newlines_in_the_reply() throws ChatException {
        String json = """
                {
                  "choices": [
                    { "message": { "role": "assistant", "content": "She said \\"hi\\".\\nLine two." } }
                  ]
                }
                """;

        assertThat(OpenAiResponseParser.parseReply(json)).isEqualTo("She said \"hi\".\nLine two.");
    }

    @Test
    public void an_empty_choices_array_is_a_chat_exception() {
        String json = "{ \"choices\": [] }";

        assertThatThrownBy(() -> OpenAiResponseParser.parseReply(json)).isInstanceOf(ChatException.class);
    }

    @Test
    public void a_missing_content_field_is_a_chat_exception() {
        String json = """
                {
                  "choices": [
                    { "message": { "role": "assistant" } }
                  ]
                }
                """;

        assertThatThrownBy(() -> OpenAiResponseParser.parseReply(json)).isInstanceOf(ChatException.class);
    }

    @Test
    public void a_missing_message_field_is_a_chat_exception() {
        String json = "{ \"choices\": [ {} ] }";

        assertThatThrownBy(() -> OpenAiResponseParser.parseReply(json)).isInstanceOf(ChatException.class);
    }

    @Test
    public void an_api_error_envelope_is_a_chat_exception_with_its_message() {
        String json = """
                {
                  "error": { "message": "Invalid API key", "type": "invalid_request_error" }
                }
                """;

        assertThatThrownBy(() -> OpenAiResponseParser.parseReply(json))
                .isInstanceOf(ChatException.class)
                .hasMessageContaining("Invalid API key");
    }

    @Test
    public void malformed_json_is_a_chat_exception() {
        assertThatThrownBy(() -> OpenAiResponseParser.parseReply("not json at all"))
                .isInstanceOf(ChatException.class);
    }

    @Test
    public void a_missing_choices_field_is_a_chat_exception() {
        assertThatThrownBy(() -> OpenAiResponseParser.parseReply("{}")).isInstanceOf(ChatException.class);
    }
}
