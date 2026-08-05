package au.bartish.game.chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A {@link ChatClient} that returns a fixed, ordered script of replies and
 * records every message list it was asked to reply to. Used by tests in any
 * module (kept in main sources deliberately - see
 * docs/TheDarkOfDarkness/conversational-npcs-implementation-plan.adoc, Phase 2 -
 * because separate Maven modules cannot share test-scoped classes without
 * extra build ceremony). It also doubles as a harmless offline/demo backend.
 */
public class ScriptedChatClient implements ChatClient {

    private final Iterator<String> script;
    private final List<List<ChatMessage>> requestsReceived = new ArrayList<>();

    public ScriptedChatClient(List<String> scriptedReplies) {
        this.script = new ArrayList<>(scriptedReplies).iterator();
    }

    public ScriptedChatClient(String... scriptedReplies) {
        this(List.of(scriptedReplies));
    }

    @Override
    public String reply(List<ChatMessage> messages) throws ChatException {
        requestsReceived.add(List.copyOf(messages));
        if (!script.hasNext()) {
            throw new ChatException("ScriptedChatClient has no more scripted replies.");
        }
        return script.next();
    }

    public List<List<ChatMessage>> getRequestsReceived() {
        return requestsReceived;
    }

    public int callCount() {
        return requestsReceived.size();
    }
}
