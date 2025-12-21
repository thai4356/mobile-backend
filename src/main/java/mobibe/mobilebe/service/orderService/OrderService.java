package mobibe.mobilebe.service.orderService;

import java.util.List;

import mobibe.mobilebe.dto.response.order.OrderRes;

public interface OrderService {

    OrderRes createOrder(int userId);

    List<OrderRes> getOrders(int userId);
}