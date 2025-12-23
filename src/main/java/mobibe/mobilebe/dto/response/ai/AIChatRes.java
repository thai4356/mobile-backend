package mobibe.mobilebe.dto.response.ai;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class AIChatRes {

    private String message;
    private List<AITagRes> tags;
    private List<AIProductRes> products;

    public AIChatRes() {
    }

    public AIChatRes(String message, List<AIProductRes> products, List<AITagRes> tags) {
        this.message = message;
        this.products = products;
        this.tags = tags;
    }
}
