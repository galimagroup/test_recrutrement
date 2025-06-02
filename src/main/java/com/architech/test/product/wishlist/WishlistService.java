package com.architech.test.product.wishlist;

import com.architech.test.product.products.Product;
import com.architech.test.product.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistService {

    private static final Logger log = LoggerFactory.getLogger(WishlistService.class);


    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public WishlistService(WishlistRepository wishlistRepository, WishlistItemRepository wishlistItemRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    public Wishlist createWishlist(Long userId, String name, String description, Boolean isPublic) {
        Wishlist wishlist = Wishlist.builder()
            .name(name)
            .description(description)
            .isPublic(isPublic != null ? isPublic : false)
            .user(User.builder().id(userId).build())
            .build();

        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getUserWishlists(Long userId) {
        return wishlistRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Optional<Wishlist> getDefaultWishlist(Long userId) {
        return wishlistRepository.findByUserIdAndIsDefaultTrue(userId);
    }

    public Wishlist getOrCreateDefaultWishlist(Long userId) {
        return getDefaultWishlist(userId)
            .orElseGet(() -> {
                Wishlist defaultWishlist = Wishlist.builder()
                    .name("My wishlist")
                    .description("Default wishlist")
                    .isDefault(true)
                    .isPublic(false)
                    .user(User.builder().id(userId).build())
                    .build();
                return wishlistRepository.save(defaultWishlist);
            });
    }

    public WishlistItem addProductToWishlist(Long wishlistId, Long productId, String note, Integer priority) {
        Optional<WishlistItem> existingItem = wishlistItemRepository.findByWishlistIdAndProductId(wishlistId, productId);
        if (existingItem.isPresent()) {
            throw new IllegalArgumentException("The product is already in this wishlist");
        }

        WishlistItem item = WishlistItem.builder()
            .wishlist(Wishlist.builder().id(wishlistId).build())
            .product(Product.builder().id(productId).build())
            .note(note)
            .priority(priority != null ? priority : 1)
            .build();

        return wishlistItemRepository.save(item);
    }

    public WishlistItem addProductToDefaultWishlist(Long userId, Long productId, String note) {
        Wishlist defaultWishlist = getOrCreateDefaultWishlist(userId);
        return addProductToWishlist(defaultWishlist.getId(), productId, note, 1);
    }

    public void removeProductFromWishlist(Long wishlistId, Long productId) {
        wishlistItemRepository.deleteByWishlistIdAndProductId(wishlistId, productId);
    }

    public List<WishlistItem> getWishlistItems(Long wishlistId) {
        return wishlistItemRepository.findByWishlistIdOrderByAddedAtDesc(wishlistId);
    }

    public boolean isProductInUserWishlists(Long userId, Long productId) {
        List<WishlistItem> items = wishlistItemRepository.findByUserIdAndProductId(userId, productId);
        return !items.isEmpty();
    }

    public Long getProductWishlistCount(Long productId) {
        return wishlistItemRepository.countByProductId(productId);
    }
}
