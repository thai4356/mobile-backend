package mobibe.mobilebe.repository.orderRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.entity.order.Order;
import mobibe.mobilebe.entity.order.QOrder;
import mobibe.mobilebe.repository.BaseRepository;

import java.util.List;

@Repository
@Transactional
public class OrderRepositoryImpl extends BaseRepository implements OrderRepositoryCustom {

    private final QOrder qOrder = QOrder.order;

    @Override
    public Order findByIdAndUserId(Long orderId, int userId) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOrder.id.eq(orderId));
        builder.and(qOrder.user.id.eq((long) userId));

        return query()
                .select(qOrder)
                .from(qOrder)
                .where(builder)
                .fetchOne();
    }
}