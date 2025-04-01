package sow.issa.recrutement.services;

import sow.issa.recrutement.models.response.ProductResponse;

import java.util.List;

public interface WishlistService {
    void addProductToWishlist(Long productId);
    List<ProductResponse> readAllItem();
    void removeProducToWishlist(Long productId);
}
