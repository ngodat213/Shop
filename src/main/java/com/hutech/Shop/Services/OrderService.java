package com.hutech.Shop.Services;

import com.hutech.Shop.model.CartItem;
import com.hutech.Shop.model.Order;
import com.hutech.Shop.model.OrderDetail;
import com.hutech.Shop.model.Product;
import com.hutech.Shop.repository.OrderDetailRepository;
import com.hutech.Shop.repository.OrderRepository;
import com.hutech.Shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService; // Assuming you have a CartService
    // @Transactional
    public Order createOrder(String customerName, List<CartItem> cartItems) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order = orderRepository.save(order);

        for (CartItem item : cartItems) {
            Product product = item.getProduct();

            // Check if the product has enough quantity
            if (product.getNums() >= item.getQuantity()) {
                // Decrement the product quantity
                product.setNums(product.getNums() - item.getQuantity());

                // Save the updated product back to the database
                productRepository.save(product);

                // Create and save order detail
                OrderDetail detail = new OrderDetail();
                detail.setOrder(order);
                detail.setProduct(product);
                detail.setQuantity(item.getQuantity());
                orderDetailRepository.save(detail);
            }
        }

        // Clear the cart after placing the order
        cartService.clearCart();

        return order;
    }
}