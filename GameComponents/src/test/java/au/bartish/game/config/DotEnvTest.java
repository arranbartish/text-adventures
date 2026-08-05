package au.bartish.game.config;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DotEnvTest {

    private Path tempFile;

    @After
    public void cleanup() throws IOException {
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    public void parses_key_value_pairs() throws IOException {
        DotEnv dotEnv = load("OPENAI_API_KEY=sk-abc123\nOPENAI_MODEL=gpt-4o-mini\n");

        assertThat(dotEnv.get("OPENAI_API_KEY", "fallback")).isEqualTo("sk-abc123");
        assertThat(dotEnv.get("OPENAI_MODEL", "fallback")).isEqualTo("gpt-4o-mini");
    }

    @Test
    public void ignores_blank_lines_and_comments() throws IOException {
        DotEnv dotEnv = load("\n# a comment\n   \nKEY=value\n# KEY2=ignored\n");

        assertThat(dotEnv.get("KEY", "fallback")).isEqualTo("value");
        assertThat(dotEnv.get("KEY2", "fallback")).isEqualTo("fallback");
    }

    @Test
    public void splits_on_first_equals_only() throws IOException {
        DotEnv dotEnv = load("A=b=c\n");

        assertThat(dotEnv.get("A", "fallback")).isEqualTo("b=c");
    }

    @Test
    public void strips_surrounding_double_or_single_quotes() throws IOException {
        DotEnv dotEnv = load("A=\"quoted value\"\nB='single quoted'\nC=unquoted\n");

        assertThat(dotEnv.get("A", "fallback")).isEqualTo("quoted value");
        assertThat(dotEnv.get("B", "fallback")).isEqualTo("single quoted");
        assertThat(dotEnv.get("C", "fallback")).isEqualTo("unquoted");
    }

    @Test
    public void real_environment_variable_beats_the_file_value() throws IOException {
        Map<String, String> fakeEnvironment = new HashMap<>();
        fakeEnvironment.put("OPENAI_API_KEY", "from-real-environment");

        tempFile = Files.createTempFile("dotenv-test", ".env");
        Files.writeString(tempFile, "OPENAI_API_KEY=from-file\n", StandardCharsets.UTF_8);

        DotEnv dotEnv = DotEnv.load(tempFile, fakeEnvironment::get);

        assertThat(dotEnv.get("OPENAI_API_KEY", "fallback")).isEqualTo("from-real-environment");
    }

    @Test
    public void missing_file_yields_the_fallback_rather_than_throwing() {
        DotEnv dotEnv = DotEnv.load(Path.of("/nonexistent/path/.env"), key -> null);

        assertThat(dotEnv.get("ANY_KEY", "fallback")).isEqualTo("fallback");
    }

    @Test
    public void toString_never_includes_loaded_values() throws IOException {
        DotEnv dotEnv = load("OPENAI_API_KEY=sk-super-secret\n");

        assertThat(dotEnv.toString()).doesNotContain("sk-super-secret");
    }

    private DotEnv load(String fileContents) throws IOException {
        tempFile = Files.createTempFile("dotenv-test", ".env");
        Files.writeString(tempFile, fileContents, StandardCharsets.UTF_8);
        return DotEnv.load(tempFile, key -> null);
    }
}
