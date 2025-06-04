package com.backend.procuct_backend.mapping;

import com.backend.procuct_backend.dto.Cart;
import com.backend.procuct_backend.entitie.CartEntity;
import org.mapstruct.Mapper;


@Mapper
public interface CartMapper {
    Cart toCart(CartEntity cartEntity);

    CartEntity fromCart(Cart cart);

    default double calculateTotalPrice(CartEntity cartEntity) {
        if (cartEntity.getItems() == null) return 0.0;
        return cartEntity.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
