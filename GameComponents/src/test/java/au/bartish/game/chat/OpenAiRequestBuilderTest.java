package au.bartish.game.chat;

import au.bartish.game.config.DotEnv;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OpenAiRequestBuilderTest {

    private final ChatConfiguration configuration = ChatConfiguration.fromDotEnv(
            DotEnv.load(Path.of("/nonexistent/.env"), key -> null));

    @Test
    public void includes_model_max_tokens_and_temperature() {
        String body = OpenAiRequestBuilder.buildRequestBody(List.of(ChatMessage.user("hi")), configuration);

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        assertThat(json.get("model").getAsString()).isEqualTo(ChatConfiguration.DEFAULT_MODEL);
        assertThat(json.get("max_tokens").getAsInt()).isEqualTo(ChatConfiguration.DEFAULT_MAX_TOKENS);
        assertThat(json.get("temperature").getAsDouble()).isEqualTo(ChatConfiguration.DEFAULT_TEMPERATURE);
    }

    @Test
    public void serialises_messages_in_order_with_roles() {
        List<ChatMessage> messages = List.of(
                ChatMessage.system("persona"),
                ChatMessage.user("hello"),
                ChatMessage.assistant("hi there"));

        String body = OpenAiRequestBuilder.buildRequestBody(messages, configuration);
        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        var array = json.getAsJsonArray("messages");

        assertThat(array).hasSize(3);
        assertThat(array.get(0).getAsJsonObject().get("role").getAsString()).isEqualTo("system");
        assertThat(array.get(0).getAsJsonObject().get("content").getAsString()).isEqualTo("persona");
        assertThat(array.get(1).getAsJsonObject().get("role").getAsString()).isEqualTo("user");
        assertThat(array.get(2).getAsJsonObject().get("role").getAsString()).isEqualTo("assistant");
    }

    @Test
    public void escapes_double_quotes_in_player_input() {
        String body = OpenAiRequestBuilder.buildRequestBody(
                List.of(ChatMessage.user("she said \"hello\" to me")), configuration);

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        String content = json.getAsJsonArray("messages").get(0).getAsJsonObject().get("content").getAsString();
        assertThat(content).isEqualTo("she said \"hello\" to me");
    }

    @Test
    public void escapes_backslashes_in_player_input() {
        String body = OpenAiRequestBuilder.buildRequestBody(
                List.of(ChatMessage.user("a\\b\\c")), configuration);

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        String content = json.getAsJsonArray("messages").get(0).getAsJsonObject().get("content").getAsString();
        assertThat(content).isEqualTo("a\\b\\c");
    }

    @Test
    public void escapes_newlines_and_tabs_in_player_input() {
        String body = OpenAiRequestBuilder.buildRequestBody(
                List.of(ChatMessage.user("line one\nline two\ttabbed")), configuration);

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        String content = json.getAsJsonArray("messages").get(0).getAsJsonObject().get("content").getAsString();
        assertThat(content).isEqualTo("line one\nline two\ttabbed");
    }

    @Test
    public void preserves_control_characters_round_trip() {
        String withControlChar = "before\u0007after";
        String body = OpenAiRequestBuilder.buildRequestBody(
                List.of(ChatMessage.user(withControlChar)), configuration);

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        String content = json.getAsJsonArray("messages").get(0).getAsJsonObject().get("content").getAsString();
        assertThat(content).isEqualTo(withControlChar);
    }

    @Test
    public void preserves_non_ascii_text() {
        String nonAscii = "héllo wörld \u00e9\u00e8 \u4f60\u597d \ud83d\ude00";
        String body = OpenAiRequestBuilder.buildRequestBody(
                List.of(ChatMessage.user(nonAscii)), configuration);

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        String content = json.getAsJsonArray("messages").get(0).getAsJsonObject().get("content").getAsString();
        assertThat(content).isEqualTo(nonAscii);
    }

    @Test
    public void produces_valid_json_even_for_an_empty_message_list() {
        String body = OpenAiRequestBuilder.buildRequestBody(List.of(), configuration);

        JsonObject json = JsonParser.parseString(body).getAsJsonObject();
        assertThat(json.getAsJsonArray("messages")).isEmpty();
    }
}
