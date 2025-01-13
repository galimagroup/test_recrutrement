package com.demba.back.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.demba.back.mappers.OrderMapper;
import com.demba.back.mappers.ProductMapper;
import com.demba.back.models.Order;
import com.demba.back.models.Product;
import com.demba.back.models.User;
import com.demba.back.payloads.OrderRequest;
import com.demba.back.payloads.OrderResponse;
import com.demba.back.repositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    public OrderResponse create(OrderRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Order order = orderRepository.save(Order.builder().user(user).build());
        List<Product> products = request.getProducts().stream()
        .map(productMapper::toProduct)
        .collect(Collectors.toList());
        order.setProducts(products);
        return orderMapper.toOrderResponse(orderRepository.save(order));
    }
}
