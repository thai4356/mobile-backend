package mobibe.mobilebe.dto.request.ai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AIChatReq {
    private String conversationId;
    private String message;
}
