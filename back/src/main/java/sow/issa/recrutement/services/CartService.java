package sow.issa.recrutement.services;

import sow.issa.recrutement.models.request.CartItemRequest;
import sow.issa.recrutement.models.response.CartItemResponse;
import sow.issa.recrutement.models.response.CartResponse;

import java.util.List;

public interface CartService {
    CartResponse addProductToCart(CartItemRequest request);
    CartResponse readAllItem();
    void removeCartItem(Long cartItemId);
}
