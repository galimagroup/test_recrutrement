package com.backend.procuct_backend.mapping;

import com.backend.procuct_backend.dto.CartItem;
import com.backend.procuct_backend.entitie.CartItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItem toCartItem(CartItemEntity cartItemEntity);


    CartItemEntity fromCartItem(CartItem cartItem);

    @Named("calculateSubtotal")
    default double calculateSubtotal(CartItemEntity cartItemEntity) {
        if (cartItemEntity == null ||
                cartItemEntity.getProduct() == null ||
                cartItemEntity.getQuantity() == null) {
            return 0.0;
        }

        Integer price = cartItemEntity.getProduct().getPrice();
        if (price == null) {
            return 0.0;
        }

        return price * cartItemEntity.getQuantity();
    }
}