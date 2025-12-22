package mobibe.mobilebe.dto.request.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderReq {

    @NotBlank(message = "Status is required")
    private String status;
}