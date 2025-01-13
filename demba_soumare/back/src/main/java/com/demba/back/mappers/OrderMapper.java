package com.demba.back.mappers;

import org.springframework.stereotype.Service;

import com.demba.back.models.Order;
import com.demba.back.payloads.OrderResponse;

@Service
public class OrderMapper {
    
    public OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
            .id(order.getId())
            .userId(order.getUser().getId())
            .build();
    }

}
