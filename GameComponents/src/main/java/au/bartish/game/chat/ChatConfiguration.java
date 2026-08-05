package au.bartish.game.chat;

import au.bartish.game.config.DotEnv;

import java.time.Duration;

/**
 * Configuration for a chat backend: model, per-reply token cap, per-session
 * turn cap, request timeout and temperature. Deliberately does not expose the
 * API key via any getter that could end up in a log line - only
 * {@code OpenAiChatClient} reads it, via {@link #getApiKey()}, and callers must
 * treat that value as a secret.
 */
public final class ChatConfiguration {

    public static final String DEFAULT_MODEL = "gpt-4o-mini";
    public static final int DEFAULT_MAX_TOKENS = 200;
    public static final int DEFAULT_MAX_TURNS = 20;
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(20);
    public static final double DEFAULT_TEMPERATURE = 0.8;

    private final String apiKey;
    private final String model;
    private final int maxTokens;
    private final int maxTurns;
    private final Duration timeout;
    private final double temperature;

    private ChatConfiguration(String apiKey, String model, int maxTokens, int maxTurns,
                               Duration timeout, double temperature) {
        this.apiKey = apiKey;
        this.model = model;
        this.maxTokens = maxTokens;
        this.maxTurns = maxTurns;
        this.timeout = timeout;
        this.temperature = temperature;
    }

    public static ChatConfiguration fromDotEnv(DotEnv dotEnv) {
        String apiKey = dotEnv.get("OPENAI_API_KEY", null);
        String model = dotEnv.get("OPENAI_MODEL", DEFAULT_MODEL);
        int maxTurns = parseIntOrDefault(dotEnv.get("NPC_MAX_TURNS", null), DEFAULT_MAX_TURNS);
        int maxTokens = parseIntOrDefault(dotEnv.get("NPC_MAX_TOKENS", null), DEFAULT_MAX_TOKENS);
        return new ChatConfiguration(apiKey, model, maxTokens, maxTurns, DEFAULT_TIMEOUT, DEFAULT_TEMPERATURE);
    }

    private static int parseIntOrDefault(String value, int fallback) {
        if (value == null) {
            return fallback;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * True when an API key has been configured, i.e. a real chat backend can be
     * constructed. Callers use this to decide between an OpenAI-backed client
     * and {@link SilentChatClient}.
     */
    public boolean hasApiKey() {
        return apiKey != null && !apiKey.isBlank();
    }

    /**
     * The configured API key. Callers must never print, log, or otherwise
     * expose this value.
     */
    public String getApiKey() {
        return apiKey;
    }

    public String getModel() {
        return model;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public int getMaxTurns() {
        return maxTurns;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public double getTemperature() {
        return temperature;
    }

    /**
     * Deliberately omits the API key, so accidental logging of a configuration
     * instance never leaks a secret.
     */
    @Override
    public String toString() {
        return "ChatConfiguration{model=" + model
                + ", maxTokens=" + maxTokens
                + ", maxTurns=" + maxTurns
                + ", timeout=" + timeout
                + ", temperature=" + temperature
                + ", hasApiKey=" + hasApiKey()
                + "}";
    }
}
