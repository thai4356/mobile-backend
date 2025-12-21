package mobibe.mobilebe.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mobibe.mobilebe.dto.request.order.CreateOrderReq;
import mobibe.mobilebe.dto.response.BaseResponse;
import mobibe.mobilebe.dto.response.order.OrderRes;
import mobibe.mobilebe.service.orderService.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse<OrderRes>> createOrder(
            @RequestBody @Valid CreateOrderReq request) {

        OrderRes data = orderService.createOrder(request.getUserId());

        return ResponseEntity.ok(
                new BaseResponse<>(data)
        );
    }
}
