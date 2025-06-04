package com.architech.test.product.cart;

import com.architech.test.product.dto.AddToCartRequest;
import com.architech.test.product.dto.CartSummaryDto;
import com.architech.test.product.dto.UpdateCartItemRequest;
import com.architech.test.product.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService) {
        this.cartService = cartService;
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Operation(summary = "REST API to get a cart for a user", description = "Get a cart for a user")
    @GetMapping
    public ResponseEntity<?> getCart() {
        String userEmail = getCurrentUserEmail();
        log.debug("REST request to get cart for user {}", userEmail);

        Cart cart = cartService.getCart(userEmail);

        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(cart)
                .message("Cart retrieved successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @Operation(summary = "REST API to get cart summary for user", description = "Get cart summary for user")
    @GetMapping("/summary")
    public ResponseEntity<?> getCartSummary() {
        String userEmail = getCurrentUserEmail();
        log.debug("REST request to get cart summary for user {}", userEmail);

        CartSummaryDto summary = cartService.getCartSummary(userEmail);

        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(summary)
                .message("Cart summary retrieved successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @Operation(summary = "REST API to add product in cart for a user", description = "Add product in cart for a user")
    @PostMapping("/items")
    public ResponseEntity<?> addToCart(@Valid @RequestBody AddToCartRequest request) {
        String userEmail = getCurrentUserEmail();
        log.debug("REST request to add product {} in cart for user {}", request.getProductId(), userEmail);

        Cart cart = cartService.addToCart(userEmail, request);

        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(cart)
                .message("Product added to cart successfully.")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @Operation(summary = "REST API to update product quantity in cart", description = "Update product quantity in cart")
    @PutMapping("/items/{productId}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long productId,
                                            @Valid @RequestBody UpdateCartItemRequest request) {
        String userEmail = getCurrentUserEmail();
        log.debug("REST request to update product {} quantity to {} in cart for user {}",
            productId, request.getQuantity(), userEmail);

        Cart cart = cartService.updateCartItem(userEmail, productId, request);

        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(cart)
                .message("Quantity updated successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @Operation(summary = "REST API to remove product from cart", description = "Remove product from cart")
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId) {
        String userEmail = getCurrentUserEmail();
        log.debug("REST request to remove product {} from cart for user {}", productId, userEmail);

        Cart cart = cartService.removeFromCart(userEmail, productId);

        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(cart)
                .message("Product removed from cart successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }

    @Operation(summary = "REST API to clear cart for user", description = "Clear cart for user")
    @DeleteMapping
    public ResponseEntity<?> clearCart() {
        String userEmail = getCurrentUserEmail();
        log.debug("REST request to clear cart for user {}", userEmail);

        cartService.clearCart(userEmail);

        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(null)
                .message("Cart cleared successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }
}
