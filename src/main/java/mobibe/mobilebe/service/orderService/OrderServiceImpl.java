
package mobibe.mobilebe.service.orderService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mobibe.mobilebe.dto.request.order.UpdateOrderReq;
import mobibe.mobilebe.dto.response.order.OrderItemRes;
import mobibe.mobilebe.dto.response.order.OrderRes;
import mobibe.mobilebe.entity.cart.Cart;
import mobibe.mobilebe.entity.cartItem.CartItem;
import mobibe.mobilebe.entity.order.Order;
import mobibe.mobilebe.entity.orderItem.OrderItem;
import mobibe.mobilebe.entity.user.User;
import mobibe.mobilebe.exceptions.BusinessException;
import mobibe.mobilebe.repository.cartRepository.CartRepository;
import mobibe.mobilebe.repository.orderRepository.OrderRepository;
import mobibe.mobilebe.repository.userRepository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mobibe.mobilebe.repository.cartItemRepository.CartItemRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public OrderRes createOrder(int userId) {

        Cart cart = cartRepository.findActiveByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            throw new BusinessException("Cart is empty");
        }

        User user = userRepository.getById(userId);

        Order order = new Order();
        order.setUser(user);
        order.setStatus("NEW");

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());

            total = total.add(
                    item.getPrice()
                            .multiply(BigDecimal.valueOf(item.getQuantity())));

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        orderRepository.save(order);
        cartItemRepository.deleteByCartId(cart.getId());
        cart.getItems().clear();

        // 3. Deactivate cart
        cart.setActive(false);
        cartRepository.save(cart);

        return toOrderRes(order);
    }

    private OrderRes toOrderRes(Order order) {

        OrderRes res = new OrderRes();
        res.setId(order.getId());
        res.setStatus(order.getStatus());
        res.setTotalAmount(order.getTotalAmount());
        res.setCreatedAt(order.getCreatedAt());

        List<OrderItemRes> items = order.getItems().stream().map(i -> {
            OrderItemRes item = new OrderItemRes();
            item.setProductId(i.getProduct().getId());
            item.setProductName(i.getProduct().getName());
            item.setQuantity(i.getQuantity());
            item.setPrice(i.getPrice());
            return item;
        }).toList();

        res.setItems(items);
        return res;
    }

    @Override
    public List<OrderRes> getOrders(int userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        if (orders == null || orders.isEmpty()) {
            throw new BusinessException("Order not found");
        }

        List<OrderRes> result = new ArrayList<>();

        for (Order order : orders) {

            OrderRes res = new OrderRes();
            res.setId(order.getId());
            res.setStatus(order.getStatus());
            res.setTotalAmount(order.getTotalAmount());
            res.setCreatedAt(order.getCreatedAt());

            List<OrderItemRes> itemResList = new ArrayList<>();

            for (OrderItem item : order.getItems()) {
                OrderItemRes itemRes = new OrderItemRes();
                itemRes.setProductId(item.getProduct().getId());
                itemRes.setProductName(item.getProduct().getName());
                itemRes.setPrice(item.getPrice());
                itemRes.setQuantity(item.getQuantity());
                itemResList.add(itemRes);
            }

            res.setItems(itemResList);
            result.add(res);
        }

        return result;
    }

    @Override
    @Transactional
    public OrderRes updateOrder(int orderId, UpdateOrderReq request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException("Order not found"));

        // Không cho sửa nếu đã kết thúc
        if ("DONE".equals(order.getStatus()) || "CANCEL".equals(order.getStatus())) {
            throw new BusinessException("Order cannot be updated");
        }

        String newStatus = request.getStatus();

        List<String> validStatus = List.of(
                "NEW", "CONFIRMED", "DELIVERING", "DONE", "CANCEL");

        if (!validStatus.contains(newStatus)) {
            throw new BusinessException("Invalid order status");
        }

        order.setStatus(newStatus);

        orderRepository.save(order);
        return toOrderRes(order);
    }

    public List<OrderRes> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToOrderRes)
                .toList();
    }

    private OrderRes mapToOrderRes(Order order) {

        OrderRes res = new OrderRes();
        res.setId(order.getId());

        res.setTotalAmount(order.getTotalAmount());

        res.setStatus(order.getStatus());
        res.setCreatedAt(order.getCreatedAt());

        // ✅ map items (KHÔNG gán thẳng entity)
        if (order.getItems() != null) {
            res.setItems(
                    order.getItems()
                            .stream()
                            .map(this::mapToOrderItemRes)
                            .toList());
        }

        return res;
    }

    private OrderItemRes mapToOrderItemRes(OrderItem item) {

        OrderItemRes res = new OrderItemRes();
        res.setProductId(item.getProduct().getId());
        res.setProductName(item.getProduct().getName());
        res.setPrice(item.getPrice());
        res.setQuantity(item.getQuantity());

        return res;
    }

    @Override
    public OrderRes getOrderDetail(int orderId) {

        Order order = orderRepository.findByOrderId(orderId);

        if (order == null) {
            throw new BusinessException("order_not_found");
        }

        return mapToOrderRes(order);
    }

}
