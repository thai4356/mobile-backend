package mobibe.mobilebe.repository.orderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import mobibe.mobilebe.entity.order.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRepositoryCustom {
    
}
