package mobibe.mobilebe.service.orderService;

import java.util.List;

import mobibe.mobilebe.dto.request.order.UpdateOrderReq;
import mobibe.mobilebe.dto.response.order.OrderRes;

public interface OrderService {

    OrderRes createOrder(int userId);

    List<OrderRes> getOrders(int userId);

    List<OrderRes> getAllOrders();

    OrderRes updateOrder(int orderId, UpdateOrderReq request);

    OrderRes getOrderDetail(int orderId);
}