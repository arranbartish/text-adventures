package au.bartish.game.chat;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * The only class in this codebase that talks to the network. Deliberately
 * thin: all request-building and response-parsing logic lives in
 * {@link OpenAiRequestBuilder} and {@link OpenAiResponseParser}, which are
 * pure and fully unit tested. This class is not unit tested itself - only its
 * two collaborators are - keeping the untestable surface area as small as
 * possible.
 *
 * Any failure (non-2xx status, timeout, network error) becomes a
 * {@link ChatException} whose message never includes the API key, the request
 * body, or any request header.
 */
public class OpenAiChatClient implements ChatClient {

    private static final URI ENDPOINT = URI.create("https://api.openai.com/v1/chat/completions");

    private final HttpClient httpClient;
    private final ChatConfiguration configuration;

    public OpenAiChatClient(ChatConfiguration configuration) {
        this(HttpClient.newHttpClient(), configuration);
    }

    OpenAiChatClient(HttpClient httpClient, ChatConfiguration configuration) {
        this.httpClient = httpClient;
        this.configuration = configuration;
    }

    @Override
    public String reply(List<ChatMessage> messages) throws ChatException {
        String requestBody = OpenAiRequestBuilder.buildRequestBody(messages, configuration);

        HttpRequest request = HttpRequest.newBuilder(ENDPOINT)
                .timeout(configuration.getTimeout())
                .header("Authorization", "Bearer " + configuration.getApiKey())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ChatException("Could not reach the chat backend.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ChatException("The chat request was interrupted.", e);
        }

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new ChatException("The chat backend returned HTTP " + response.statusCode() + ".");
        }

        return OpenAiResponseParser.parseReply(response.body());
    }
}
