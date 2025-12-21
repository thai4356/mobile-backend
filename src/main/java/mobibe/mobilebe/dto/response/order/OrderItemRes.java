package mobibe.mobilebe.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemRes {

    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
