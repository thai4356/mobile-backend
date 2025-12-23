package mobibe.mobilebe.dto.request.tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagReq {
    private String name; // gym, tăng cơ
    private String type; // SPORT, GOAL, LEVEL
}
