package au.bartish.game.chat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Parses a JSON response body from the OpenAI Chat Completions endpoint into
 * the assistant's reply text. Pure and side-effect free, so it is fully unit
 * testable without any network access.
 */
public final class OpenAiResponseParser {

    private OpenAiResponseParser() {
    }

    public static String parseReply(String responseBody) throws ChatException {
        JsonObject root = parseObject(responseBody);

        if (root.has("error")) {
            throw new ChatException("The chat backend returned an error: " + errorMessage(root));
        }

        JsonElement choicesElement = root.get("choices");
        if (choicesElement == null || !choicesElement.isJsonArray()) {
            throw new ChatException("The chat backend response had no choices.");
        }

        JsonArray choices = choicesElement.getAsJsonArray();
        if (choices.isEmpty()) {
            throw new ChatException("The chat backend response had an empty choices array.");
        }

        JsonObject firstChoice = choices.get(0).getAsJsonObject();
        JsonElement messageElement = firstChoice.get("message");
        if (messageElement == null || !messageElement.isJsonObject()) {
            throw new ChatException("The chat backend response had no message on the first choice.");
        }

        JsonElement contentElement = messageElement.getAsJsonObject().get("content");
        if (contentElement == null || !contentElement.isJsonPrimitive()) {
            throw new ChatException("The chat backend response had no content on the message.");
        }

        return contentElement.getAsString();
    }

    private static JsonObject parseObject(String responseBody) throws ChatException {
        try {
            JsonElement parsed = JsonParser.parseString(responseBody);
            if (!parsed.isJsonObject()) {
                throw new ChatException("The chat backend response was not a JSON object.");
            }
            return parsed.getAsJsonObject();
        } catch (JsonSyntaxException e) {
            throw new ChatException("The chat backend response could not be parsed.", e);
        }
    }

    private static String errorMessage(JsonObject root) {
        JsonElement error = root.get("error");
        if (error.isJsonObject()) {
            JsonElement message = error.getAsJsonObject().get("message");
            if (message != null && message.isJsonPrimitive()) {
                return message.getAsString();
            }
        }
        return "unknown error";
    }
}
