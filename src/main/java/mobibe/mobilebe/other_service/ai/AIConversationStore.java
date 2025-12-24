package mobibe.mobilebe.other_service.ai;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AIConversationStore {

    private final Map<String, AIConversationContext> store = new ConcurrentHashMap<>();

    public AIConversationContext get(String conversationId) {
        return store.computeIfAbsent(conversationId, k -> new AIConversationContext());
    }
}
