package mobibe.mobilebe.dto.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderReq {

    @NotNull
    private int userId;
}
