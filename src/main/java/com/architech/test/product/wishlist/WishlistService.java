package com.architech.test.product.wishlist;

import java.util.List;
import java.util.Optional;

public interface WishlistService {

    Wishlist createWishlist(Long userId, String name, String description, Boolean isPublic);
    List<Wishlist> getUserWishlists(Long userId);
    Optional<Wishlist> getDefaultWishlist(Long userId);
    Wishlist getOrCreateDefaultWishlist(Long userId);
    void addProductToWishlist(Long wishlistId, Long productId, String note, Integer priority);
    void addProductToDefaultWishlist(Long userId, Long productId, String note);
    void removeProductFromWishlist(Long wishlistId, Long productId);
    List<WishlistItem> getWishlistItems(Long wishlistId);
    boolean isProductInUserWishlists(Long userId, Long productId);
}
