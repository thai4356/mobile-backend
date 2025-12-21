package mobibe.mobilebe.repository.cartRepository;

import org.springframework.stereotype.Repository;

import mobibe.mobilebe.entity.cart.Cart;

@Repository
public interface CartRepositoryCustom {

    Cart findActiveByUserId(int userId);
    
}
