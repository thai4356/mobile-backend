package mobibe.mobilebe.controller;

import mobibe.mobilebe.dto.request.otp.SendOtpReq;
import mobibe.mobilebe.dto.response.cart.CartDetailRes;
import mobibe.mobilebe.entity.cart.Cart;
import mobibe.mobilebe.service.cart.CartService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mobibe.mobilebe.dto.request.cart.AddToCartReq;
import mobibe.mobilebe.dto.request.cart.RemoveCartItemReq;
import mobibe.mobilebe.dto.request.cart.UpdateCartReq;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDetailRes> getMyCart(
            @PathVariable int userId) {
        return ResponseEntity.ok(cartService.getMyCart(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<CartDetailRes> addItem(
            @RequestBody @Valid AddToCartReq request) {
        return ResponseEntity.ok(
                cartService.addItem(request));
    }

    @PutMapping("/update")
    public ResponseEntity<CartDetailRes> updateItem(
            @RequestBody @Valid UpdateCartReq request) {
        return ResponseEntity.ok(
                cartService.updateItem(request));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartDetailRes> removeItem(
            @RequestBody RemoveCartItemReq request) {

        return ResponseEntity.ok(
                cartService.removeItem(
                        request.getUserId(),
                        request.getProductId()));
    }
}
