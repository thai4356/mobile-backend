package mobibe.mobilebe.dto.request.review;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteReviewReq {

    @NotNull
    private int userId;
}
