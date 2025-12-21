package mobibe.mobilebe.dto.request.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddReviewReq {

    @NotNull
    private int productId;

    @NotNull
    private int userId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;
}
