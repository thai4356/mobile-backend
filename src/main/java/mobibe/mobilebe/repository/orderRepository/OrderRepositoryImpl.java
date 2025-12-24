package mobibe.mobilebe.repository.orderRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.entity.order.Order;
import mobibe.mobilebe.entity.order.QOrder;
import mobibe.mobilebe.repository.BaseRepository;
import mobibe.mobilebe.dto.response.order.OrderRes;

import java.util.List;

@Repository
@Transactional
public class OrderRepositoryImpl extends BaseRepository implements OrderRepositoryCustom {

    private final QOrder qOrder = QOrder.order;

    @Override
    public List<Order> findByUserId(int userId) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and((qOrder.user.id).eq(userId));
        builder.and((qOrder.deleted).isFalse());

        return query()
                .select(qOrder)
                .from(qOrder)
                .where(builder)
                .fetch();
    }

    @Override
    public Order findByOrderId(int orderId) {

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qOrder.id.eq(orderId));
        builder.and(qOrder.deleted.isFalse());

        return query()
                .select(qOrder)
                .from(qOrder)
                .where(builder)
                .fetchOne();
    }
}