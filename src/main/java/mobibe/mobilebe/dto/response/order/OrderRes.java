package mobibe.mobilebe.dto.response.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderRes {

    private int id;
    private String status;
    private BigDecimal totalAmount;
    private Date createdAt;
    private List<OrderItemRes> items;
}
