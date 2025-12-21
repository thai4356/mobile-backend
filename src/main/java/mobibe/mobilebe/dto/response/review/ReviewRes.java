package mobibe.mobilebe.dto.response.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRes {

    private int id;
    private int rating;
    private String comment;
    private int userId;
    private int productId;
}