package mobibe.mobilebe.dto.response.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemRes {

    private int cartItemId;

    private int productId;

    private String productName;

    private BigDecimal productPrice;

    private int quantity;

    private BigDecimal totalPrice;
}
