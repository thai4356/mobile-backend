package mobibe.mobilebe.service.orderService;

import mobibe.mobilebe.dto.response.order.OrderRes;

public interface OrderService {

    OrderRes createOrder(int userId);
}