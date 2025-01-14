package com.galimagroup.back.serviceImpl;

import com.galimagroup.back.entities.Wishlist;
import com.galimagroup.back.entities.WishlistItem;
import com.galimagroup.back.repository.WishlistRepository;
import com.galimagroup.back.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public Wishlist getWishlistByUserId(String userId) {
        return wishlistRepository.findByUserId(userId).orElseGet(() -> {
            Wishlist newWishlist = new Wishlist();
            newWishlist.setUserId(userId);
            return wishlistRepository.save(newWishlist);
        });
    }

    @Override
    public Wishlist addItemToWishlist(String userId, WishlistItem item) {
        Wishlist wishlist = getWishlistByUserId(userId);

        boolean alreadyExists = wishlist.getItems().stream()
                .anyMatch(wishlistItem -> wishlistItem.getProductId().equals(item.getProductId()));

        if (!alreadyExists) {
            wishlist.getItems().add(item);
        }

        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist removeItemFromWishlist(String userId, String productId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        wishlist.getItems().removeIf(item -> item.getProductId().equals(productId));
        return wishlistRepository.save(wishlist);
    }

    @Override
    public void clearWishlist(String userId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        wishlist.getItems().clear();
        wishlistRepository.save(wishlist);
    }
}
