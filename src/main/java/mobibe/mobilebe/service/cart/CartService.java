package mobibe.mobilebe.service.cart;

import org.springframework.stereotype.Service;

import mobibe.mobilebe.dto.request.cart.AddToCartReq;
import mobibe.mobilebe.dto.request.cart.UpdateCartReq;
import mobibe.mobilebe.dto.response.cart.CartDetailRes;
import mobibe.mobilebe.entity.cart.Cart;

@Service
public interface CartService {

    CartDetailRes  getMyCart(int userId);

    CartDetailRes  addItem(AddToCartReq request);

    CartDetailRes  updateItem(UpdateCartReq request);

    CartDetailRes removeItem(int userId, int productId);
    
}
