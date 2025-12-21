package mobibe.mobilebe.repository.cartItemRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mobibe.mobilebe.entity.cartItem.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>,CartItemRepositoryCustom {
    void deleteByCartId(int cartId);
}
