package au.bartish.game.chat;

import au.bartish.game.config.DotEnv;
import org.junit.Test;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ChatConfigurationTest {

    @Test
    public void applies_defaults_when_nothing_is_configured() {
        DotEnv empty = DotEnv.load(Path.of("/nonexistent/.env"), key -> null);

        ChatConfiguration config = ChatConfiguration.fromDotEnv(empty);

        assertThat(config.getModel()).isEqualTo(ChatConfiguration.DEFAULT_MODEL);
        assertThat(config.getMaxTokens()).isEqualTo(ChatConfiguration.DEFAULT_MAX_TOKENS);
        assertThat(config.getMaxTurns()).isEqualTo(ChatConfiguration.DEFAULT_MAX_TURNS);
        assertThat(config.getTimeout()).isEqualTo(ChatConfiguration.DEFAULT_TIMEOUT);
        assertThat(config.getTemperature()).isEqualTo(ChatConfiguration.DEFAULT_TEMPERATURE);
        assertThat(config.hasApiKey()).isFalse();
    }

    @Test
    public void honours_overrides() {
        DotEnv fake = DotEnv.load(Path.of("/nonexistent/.env"), key -> switch (key) {
            case "OPENAI_API_KEY" -> "sk-test-key";
            case "OPENAI_MODEL" -> "gpt-4o";
            case "NPC_MAX_TURNS" -> "5";
            case "NPC_MAX_TOKENS" -> "50";
            default -> null;
        });

        ChatConfiguration config = ChatConfiguration.fromDotEnv(fake);

        assertThat(config.getModel()).isEqualTo("gpt-4o");
        assertThat(config.getMaxTurns()).isEqualTo(5);
        assertThat(config.getMaxTokens()).isEqualTo(50);
        assertThat(config.hasApiKey()).isTrue();
        assertThat(config.getApiKey()).isEqualTo("sk-test-key");
    }

    @Test
    public void falls_back_to_defaults_on_unparseable_numbers() {
        DotEnv fake = DotEnv.load(Path.of("/nonexistent/.env"), key -> switch (key) {
            case "NPC_MAX_TURNS" -> "not-a-number";
            default -> null;
        });

        ChatConfiguration config = ChatConfiguration.fromDotEnv(fake);

        assertThat(config.getMaxTurns()).isEqualTo(ChatConfiguration.DEFAULT_MAX_TURNS);
    }

    @Test
    public void blank_api_key_counts_as_not_configured() {
        DotEnv fake = DotEnv.load(Path.of("/nonexistent/.env"), key -> switch (key) {
            case "OPENAI_API_KEY" -> "   ";
            default -> null;
        });

        ChatConfiguration config = ChatConfiguration.fromDotEnv(fake);

        assertThat(config.hasApiKey()).isFalse();
    }

    @Test
    public void toString_never_includes_the_api_key() {
        DotEnv fake = DotEnv.load(Path.of("/nonexistent/.env"), key -> switch (key) {
            case "OPENAI_API_KEY" -> "sk-super-secret";
            default -> null;
        });

        ChatConfiguration config = ChatConfiguration.fromDotEnv(fake);

        assertThat(config.toString()).doesNotContain("sk-super-secret");
    }
}
