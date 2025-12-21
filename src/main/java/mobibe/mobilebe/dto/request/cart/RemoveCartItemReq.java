package mobibe.mobilebe.dto.request.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoveCartItemReq {
    private int userId;
    private int productId;
}
