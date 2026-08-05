package au.bartish.game.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Minimal ".env" file reader. Resolution order for {@link #get(String, String)}
 * is: a real environment variable first, then a matching entry from the file,
 * then the supplied fallback. This keeps CI/production working from real
 * environment variables while letting a local ".env" file supply the same keys
 * for a developer.
 *
 * The file is never required - a missing or unreadable file simply yields an
 * empty set of entries rather than throwing.
 */
public class DotEnv {

    private final Map<String, String> entries;
    private final Function<String, String> environmentLookup;

    private DotEnv(Map<String, String> entries, Function<String, String> environmentLookup) {
        this.entries = entries;
        this.environmentLookup = environmentLookup;
    }

    /**
     * Loads {@code .env} from the current working directory, if present.
     */
    public static DotEnv load() {
        return load(Path.of(".env"));
    }

    public static DotEnv load(Path path) {
        return load(path, System::getenv);
    }

    /**
     * Testing seam: allows a fake environment lookup so tests do not depend on
     * the real process environment.
     */
    public static DotEnv load(Path path, Function<String, String> environmentLookup) {
        return new DotEnv(parse(path), environmentLookup);
    }

    public String get(String key, String fallback) {
        String fromEnvironment = environmentLookup.apply(key);
        if (fromEnvironment != null) {
            return fromEnvironment;
        }
        return entries.getOrDefault(key, fallback);
    }

    private static Map<String, String> parse(Path path) {
        Map<String, String> result = new LinkedHashMap<>();
        if (path == null || !Files.isReadable(path)) {
            return result;
        }
        try {
            for (String line : Files.readAllLines(path)) {
                parseLine(line, result);
            }
        } catch (IOException e) {
            return new LinkedHashMap<>();
        }
        return result;
    }

    private static void parseLine(String rawLine, Map<String, String> result) {
        String line = rawLine.trim();
        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }
        int separator = line.indexOf('=');
        if (separator < 0) {
            return;
        }
        String key = line.substring(0, separator).trim();
        String value = unquote(line.substring(separator + 1).trim());
        if (!key.isEmpty()) {
            result.put(key, value);
        }
    }

    private static String unquote(String value) {
        if (value.length() >= 2) {
            char first = value.charAt(0);
            char last = value.charAt(value.length() - 1);
            if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
                return value.substring(1, value.length() - 1);
            }
        }
        return value;
    }

    /**
     * Deliberately does not include the loaded values, so accidental logging of
     * a DotEnv instance can never leak a secret.
     */
    @Override
    public String toString() {
        return "DotEnv{" + entries.size() + " entries loaded from file}";
    }
}
