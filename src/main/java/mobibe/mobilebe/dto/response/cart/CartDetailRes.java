package mobibe.mobilebe.dto.response.cart;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.math.BigDecimal;
@Getter
@Setter
public class CartDetailRes {
    private int cartId;

    private int userId;

    private boolean active;

    private List<CartItemRes> items;

    private int totalQuantity;

    private BigDecimal totalPrice;
}
