package com.architech.test.product.wishlist;

import com.architech.test.product.products.Product;
import com.architech.test.product.users.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService{

    private static final Logger log = LoggerFactory.getLogger(WishlistServiceImpl.class);


    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public WishlistServiceImpl(WishlistRepository wishlistRepository, WishlistItemRepository wishlistItemRepository) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
    }

    @Override
    public Wishlist createWishlist(Long userId, String name, String description, Boolean isPublic) {
        log.info("Request to create a new wishlist.");
        Wishlist wishlist = Wishlist.builder()
            .name(name)
            .description(description)
            .isPublic(isPublic != null ? isPublic : false)
            .user(User.builder().id(userId).build())
            .build();

        return wishlistRepository.save(wishlist);
    }

    @Override
    public List<Wishlist> getUserWishlists(Long userId) {
        log.info("Request to get the user's wishlist.");
        return wishlistRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public Optional<Wishlist> getDefaultWishlist(Long userId) {
        log.info("Request to get default wishlist");
        return wishlistRepository.findByUserIdAndIsDefaultTrue(userId);
    }

    @Override
    public Wishlist getOrCreateDefaultWishlist(Long userId) {
        log.info("Request to get or create a default wishlist.");
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

    @Override
    public void addProductToWishlist(Long wishlistId, Long productId, String note, Integer priority) {
        log.info("Request to add products to wishlist.");
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

        wishlistItemRepository.save(item);
    }

    @Override
    public void addProductToDefaultWishlist(Long userId, Long productId, String note) {
        log.info("Request to add Products to default wishlist");
        Wishlist defaultWishlist = getOrCreateDefaultWishlist(userId);
        addProductToWishlist(defaultWishlist.getId(), productId, note, 1);
    }

    @Override
    public void removeProductFromWishlist(Long wishlistId, Long productId) {
        log.info("Request to remove product from wishlist.");
        wishlistItemRepository.deleteByWishlistIdAndProductId(wishlistId, productId);
    }

    @Override
    public List<WishlistItem> getWishlistItems(Long wishlistId) {
        log.info("Request to get wishlist items.");
        return wishlistItemRepository.findByWishlistIdOrderByAddedAtDesc(wishlistId);
    }

    @Override
    public boolean isProductInUserWishlists(Long userId, Long productId) {
        log.info("Request to verify if product is in user wishlist.");
        List<WishlistItem> items = wishlistItemRepository.findByUserIdAndProductId(userId, productId);
        return !items.isEmpty();
    }

    public Long getProductWishlistCount(Long productId) {
        return wishlistItemRepository.countByProductId(productId);
    }
}
