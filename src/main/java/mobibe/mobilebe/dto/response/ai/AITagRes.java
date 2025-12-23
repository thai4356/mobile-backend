package mobibe.mobilebe.dto.response.ai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AITagRes {
    private String name;
    private String type; // SPORT, GOAL

    public AITagRes() {
    }

    public AITagRes(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}