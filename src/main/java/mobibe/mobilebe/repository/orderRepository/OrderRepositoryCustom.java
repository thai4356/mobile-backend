package mobibe.mobilebe.repository.orderRepository;

import mobibe.mobilebe.entity.order.Order;

import java.util.List;

public interface OrderRepositoryCustom {

    Order findByIdAndUserId(Long orderId, int userId);

}
