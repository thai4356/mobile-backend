package mobibe.mobilebe.dto.request.cart;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddToCartReq {
    @NotNull
    int userId;
    @NotNull
    int cartId;
    @NotNull
    int productId;
    int quantity;
}
