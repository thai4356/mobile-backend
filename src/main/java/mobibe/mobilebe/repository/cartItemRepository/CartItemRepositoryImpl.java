package mobibe.mobilebe.repository.cartItemRepository;

import com.querydsl.core.BooleanBuilder;
import mobibe.mobilebe.entity.cartItem.CartItem;
import mobibe.mobilebe.entity.cartItem.QCartItem;
import mobibe.mobilebe.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartItemRepositoryImpl extends BaseRepository implements CartItemRepositoryCustom {

    private final QCartItem cartItem = QCartItem.cartItem;

    @Override
    public List<CartItem> findByCartId(int cartId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cartItem.deleted.isFalse());
        builder.and(cartItem.cart.id.eq(cartId));

        return query()
                .from(cartItem)
                .where(builder)
                .select(cartItem)
                .fetch();
    }

    @Override
    public CartItem findByCartIdAndProductId(int cartId, int productId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cartItem.deleted.isFalse());
        builder.and(cartItem.cart.id.eq(cartId));
        builder.and(cartItem.product.id.eq(productId));

        return query()
                .from(cartItem)
                .where(builder)
                .select(cartItem)
                .fetchOne();
    }
}
