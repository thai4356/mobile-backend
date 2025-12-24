package mobibe.mobilebe.repository.orderRepository;

import mobibe.mobilebe.entity.order.Order;

import java.util.List;

import mobibe.mobilebe.dto.response.order.OrderRes;

public interface OrderRepositoryCustom {

    List<Order> findByUserId( int userId);

    Order findByOrderId(int orderId);

}
