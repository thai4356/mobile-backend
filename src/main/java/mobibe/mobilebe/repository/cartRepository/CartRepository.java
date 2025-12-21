package mobibe.mobilebe.repository.cartRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mobibe.mobilebe.entity.cart.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>,CartRepositoryCustom {
    Cart findByUserId(int userId);
}
