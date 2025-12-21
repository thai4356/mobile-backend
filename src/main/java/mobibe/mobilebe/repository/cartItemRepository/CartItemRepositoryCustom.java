package mobibe.mobilebe.repository.cartItemRepository;

import java.util.List;

import mobibe.mobilebe.entity.cartItem.CartItem;

public interface CartItemRepositoryCustom {

    List<CartItem> findByCartId(int cartId);

    CartItem findByCartIdAndProductId(int cartId, int productId);
}
