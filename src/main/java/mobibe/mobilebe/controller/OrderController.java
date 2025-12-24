package mobibe.mobilebe.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import mobibe.mobilebe.dto.request.order.CreateOrderReq;
import mobibe.mobilebe.dto.request.order.UpdateOrderReq;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.order.OrderRes;
import mobibe.mobilebe.service.orderService.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@EnableMethodSecurity
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderRes>> getAllOrders() {
        return ResponseEntity.ok(
                orderService.getAllOrders());
    }

    @PostMapping
    public ResponseEntity<BaseResponse<OrderRes>> createOrder(
            @RequestBody @Valid CreateOrderReq request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int userId = Integer.parseInt(auth.getName());

        OrderRes data = orderService.createOrder(userId);

        return ResponseEntity.ok(new BaseResponse<>(data));
    }

    @GetMapping("/my")
    public ResponseEntity<BaseResponse<List<OrderRes>>> getMyOrders() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        int userId = Integer.parseInt(auth.getName());

        return ResponseEntity.ok(
                new BaseResponse<>(orderService.getOrders(userId)));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<BaseResponse<OrderRes>> updateOrder(
            @PathVariable int orderId,
            @RequestBody UpdateOrderReq request) {

        return ResponseEntity.ok(
                new BaseResponse<>(orderService.updateOrder(orderId, request)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderRes> getOrderDetail(
            @PathVariable int orderId) {

        return ResponseEntity.ok(
                orderService.getOrderDetail(orderId));
    }

}
