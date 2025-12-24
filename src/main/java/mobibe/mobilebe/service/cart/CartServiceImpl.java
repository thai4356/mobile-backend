package mobibe.mobilebe.service.cart;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.entity.cart.Cart;
import mobibe.mobilebe.entity.cartItem.CartItem;
import mobibe.mobilebe.entity.product.Product;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.BaseRepository;
import mobibe.mobilebe.repository.cartItemRepository.CartItemRepository;
import mobibe.mobilebe.repository.cartItemRepository.CartItemRepositoryCustom;
import mobibe.mobilebe.repository.cartRepository.CartRepository;
import mobibe.mobilebe.repository.cartRepository.CartRepositoryCustom;
import mobibe.mobilebe.repository.productRepository.ProductRepository;
import mobibe.mobilebe.repository.productRepository.ProductRepositoryCustom;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mobibe.mobilebe.dto.request.cart.AddToCartReq;
import mobibe.mobilebe.dto.request.cart.UpdateCartReq;
import mobibe.mobilebe.dto.response.cart.CartDetailRes;
import mobibe.mobilebe.dto.response.cart.CartItemRes;
import mobibe.mobilebe.repository.userRepository.UserRepository;

@Service
@Transactional
public class CartServiceImpl extends BaseRepository implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository,
            ProductRepository productRepository, UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartDetailRes getMyCart(int userId) {

        // 1. Ưu tiên cart active
        Cart cart = cartRepository.findActiveByUserId(userId);

        if (cart == null) {
            Cart existingCart = cartRepository.findByUserId(userId);
            if (existingCart != null) {
                existingCart.setActive(true);
                existingCart.setDeleted(false);
                cart = cartRepository.save(existingCart);
            } else {
                cart = new Cart();
                cart.setActive(true);
                cart.setDeleted(false);
                cart.setItems(new ArrayList<>());
                cart.setUser(userRepository.getById(userId));
                cart = cartRepository.save(cart);
            }
        }

        // ===== PHẦN DƯỚI GIỮ NGUYÊN =====

        CartDetailRes res = new CartDetailRes();
        res.setCartId(cart.getId());
        res.setUserId(cart.getUser().getId());
        res.setActive(cart.isActive());

        List<CartItemRes> itemResList = new ArrayList<>();
        int totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;

        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {

                CartItemRes itemRes = new CartItemRes();
                itemRes.setCartItemId(item.getId());
                itemRes.setProductId(item.getProduct().getId());
                itemRes.setProductName(item.getProduct().getName());
                itemRes.setProductPrice(item.getProduct().getPrice());
                itemRes.setQuantity(item.getQuantity());

                BigDecimal itemTotal = item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));

                itemRes.setTotalPrice(itemTotal);

                totalQuantity += item.getQuantity();
                totalPrice = totalPrice.add(itemTotal);

                itemResList.add(itemRes);
            }
        }

        res.setItems(itemResList);
        res.setTotalQuantity(totalQuantity);
        res.setTotalPrice(totalPrice);

        return res;
    }

    @Override
    public CartDetailRes addItem(AddToCartReq request) {
        Cart cart = cartRepository.findActiveByUserId(request.getUserId());

        if (cart == null) {
            cart = new Cart();
            cart.setActive(true);
            cart.setItems(new ArrayList<>());
            cart.setUser(userRepository.getById(request.getUserId()));
            cartRepository.save(cart);
        }

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductId());

        if (item != null) {
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
        } else {
            Product  product = productRepository.findOneActiveById(request.getProductId());
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity()); 
            cartItemRepository.save(newItem);
            cart.getItems().add(newItem);
        }

        // build response TRỰC TIẾP
        CartDetailRes res = new CartDetailRes();
        res.setCartId(cart.getId());
        res.setUserId(cart.getUser().getId());
        res.setActive(cart.isActive());

        List<CartItemRes> itemResList = new ArrayList<>();
        int totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CartItem ci : cart.getItems()) {
            CartItemRes ir = new CartItemRes();
            ir.setCartItemId(ci.getId());
            ir.setProductId(ci.getProduct().getId());
            ir.setProductName(ci.getProduct().getName());
            ir.setProductPrice(ci.getProduct().getPrice());
            ir.setQuantity(ci.getQuantity());

            BigDecimal itemTotal = ci.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(ci.getQuantity()));

            ir.setTotalPrice(itemTotal);

            totalQuantity += ci.getQuantity();
            totalPrice = totalPrice.add(itemTotal);

            itemResList.add(ir);
        }

        res.setItems(itemResList);
        res.setTotalQuantity(totalQuantity);
        res.setTotalPrice(totalPrice);

        return res;
    }

    @Override
    public CartDetailRes updateItem(UpdateCartReq request) {
        Cart cart = cartRepository.findActiveByUserId(request.getUserId());

        if (cart == null) {
            throw new BusinessException("Cart not found");
        }

        CartItem item = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductId());

        if (item == null) {
            throw new BusinessException("Cart item not found");
        }

        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);

        return getMyCart(request.getUserId());
    }

    @Override
    @Transactional
    public CartDetailRes removeItem(int userId, int productId) {
        Cart cart = cartRepository.findActiveByUserId(userId);

        if (cart == null) {
            throw new BusinessException("Cart not found");
        }

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId);

        if (item == null) {
            throw new BusinessException("Cart item not found");
        }

        cart.getItems().remove(item);

        // optional nhưng nên có
        cartRepository.save(cart);

        return getMyCart(userId);
    }

}
