package au.bartish.game.chat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Builds the JSON request body for the OpenAI Chat Completions endpoint.
 * Pure and side-effect free, so it is fully unit testable without any network
 * access. Uses Gson to build the JSON tree directly (rather than hand-rolling
 * string concatenation) specifically so that player-typed content - which may
 * contain quotes, backslashes, newlines or other control characters - is
 * always escaped correctly.
 */
public final class OpenAiRequestBuilder {

    private OpenAiRequestBuilder() {
    }

    public static String buildRequestBody(List<ChatMessage> messages, ChatConfiguration configuration) {
        JsonObject body = new JsonObject();
        body.addProperty("model", configuration.getModel());
        body.addProperty("max_tokens", configuration.getMaxTokens());
        body.addProperty("temperature", configuration.getTemperature());

        JsonArray messagesArray = new JsonArray();
        for (ChatMessage message : messages) {
            JsonObject messageObject = new JsonObject();
            messageObject.addProperty("role", message.getRole().getWireName());
            messageObject.addProperty("content", message.getContent());
            messagesArray.add(messageObject);
        }
        body.add("messages", messagesArray);

        return body.toString();
    }
}
