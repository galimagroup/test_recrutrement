package com.architech.test.product.wishlist;

import com.architech.test.product.cart.CartController;
import com.architech.test.product.dto.*;
import com.architech.test.product.products.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private static final Logger log = LoggerFactory.getLogger(WishlistController.class);


    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistResponseDTO>> getUserWishlists(@PathVariable Long userId) {
        log.info("RESt request to get user wishlists.");
        List<Wishlist> wishlists = wishlistService.getUserWishlists(userId);
        List<WishlistResponseDTO> response = wishlists.stream()
            .map(this::convertToDTO)
            .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<WishlistResponseDTO> createWishlist(@RequestBody CreateWishlistRequest request) {
        log.info("REST request to create a wishlist.");
        Wishlist wishlist = wishlistService.createWishlist(
            request.getUserId(),
            request.getName(),
            request.getDescription(),
            request.getIsPublic()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(wishlist));
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<WishlistResponseDTO> getWishlist(@PathVariable Long wishlistId) {
        log.info("REST request to get wishlist by id: {}", wishlistId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{wishlistId}/items")
    public ResponseEntity<String> addProductToWishlist(
        @PathVariable Long wishlistId,
        @RequestBody AddProductToWishlistRequest request) {
        log.info("REST request to add product in wishlist.");
        try {
            wishlistService.addProductToWishlist(
                wishlistId,
                request.getProductId(),
                request.getNote(),
                request.getPriority()
            );
            return ResponseEntity.ok("Product added to wishlist successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/default/items")
    public ResponseEntity<String> addProductToDefaultWishlist(@RequestBody AddProductToDefaultWishlistRequest request) {
        log.info("REST request to add product to default wishlist. ");
        try {
            wishlistService.addProductToDefaultWishlist(
                request.getUserId(),
                request.getProductId(),
                request.getNote()
            );
            return ResponseEntity.ok("Product added to default wishlist successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding product.");
        }
    }

    @DeleteMapping("/{wishlistId}/items/{productId}")
    public ResponseEntity<String> removeProductFromWishlist(
        @PathVariable Long wishlistId,
        @PathVariable Long productId) {
        log.info("REST request to remove product from wishlist.");
        wishlistService.removeProductFromWishlist(wishlistId, productId);
        return ResponseEntity.ok("Product removed from wishlist successfully.");
    }

    @GetMapping("/{wishlistId}/items")
    public ResponseEntity<List<WishlistItemResponseDTO>> getWishlistItems(@PathVariable Long wishlistId) {
        log.info("REST request to get all wishlist items.");
        List<WishlistItem> items = wishlistService.getWishlistItems(wishlistId);
        List<WishlistItemResponseDTO> response = items.stream()
            .map(this::convertItemToDTO)
            .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check/{userId}/{productId}")
    public ResponseEntity<Boolean> isProductInWishlists(
        @PathVariable Long userId,
        @PathVariable Long productId) {
        log.info("REST request to know if product is in wishlist.");
        boolean isInWishlist = wishlistService.isProductInUserWishlists(userId, productId);
        return ResponseEntity.ok(isInWishlist);
    }

    private WishlistResponseDTO convertToDTO(Wishlist wishlist) {
        return WishlistResponseDTO.builder()
            .id(wishlist.getId())
            .name(wishlist.getName())
            .description(wishlist.getDescription())
            .isDefault(wishlist.getIsDefault())
            .isPublic(wishlist.getIsPublic())
            .createdAt(wishlist.getCreatedAt())
            .updatedAt(wishlist.getUpdatedAt())
            .itemCount(wishlist.getItems() != null ? wishlist.getItems().size() : 0)
            .build();
    }

    private WishlistItemResponseDTO convertItemToDTO(WishlistItem item) {
        return WishlistItemResponseDTO.builder()
            .id(item.getId())
            .note(item.getNote())
            .priority(item.getPriority())
            .addedAt(item.getAddedAt())
            .product(convertProductToSummary(item.getProduct()))
            .build();
    }

    private ProductSummaryDTO convertProductToSummary(Product product) {
        return ProductSummaryDTO.builder()
            .id(product.getId())
            .code(product.getCode())
            .name(product.getName())
            .image(product.getImage())
            .price(product.getPrice().intValue())
            .category(product.getCategory())
            .inventoryStatus(product.getInventoryStatus())
            .build();
    }
}
