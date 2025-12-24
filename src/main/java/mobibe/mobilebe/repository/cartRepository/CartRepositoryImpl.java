package mobibe.mobilebe.repository.cartRepository;

import com.querydsl.core.BooleanBuilder;
import mobibe.mobilebe.entity.cart.Cart;
import mobibe.mobilebe.entity.cart.QCart;
import mobibe.mobilebe.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepositoryImpl extends BaseRepository
        implements CartRepositoryCustom {

    private final QCart cart = QCart.cart;

    @Override
    public Cart findActiveByUserId(int userId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cart.deleted.isFalse());
        builder.and(cart.user.id.eq(userId));

        return query()
                .from(cart)
                .where(builder)
                .select(cart)
                .fetchOne();
    }
}
