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

    private List<String> suggestions;

    private Integer maxBudget;

   
}
