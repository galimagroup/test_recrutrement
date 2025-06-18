package com.architech.test.product.cart;

import com.architech.test.product.dto.AddToCartRequest;
import com.architech.test.product.dto.CartSummaryDto;
import com.architech.test.product.dto.UpdateCartItemRequest;
import com.architech.test.product.products.Product;
import com.architech.test.product.products.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    @Override
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId) );
    }

    @Override
    public Cart getOrCreateCart(String userEmail) {
        return cartRepository.findByUserEmailWithItems(userEmail)
            .orElseGet(() -> {
                log.info("Creating a new cart for user: {}", userEmail);
                Cart newCart = Cart.builder()
                    .userEmail(userEmail)
                    .build();
                return cartRepository.save(newCart);
            });
    }

    @Override
    public Cart addToCart(String userEmail, AddToCartRequest request) {
        Cart cart = getOrCreateCart(userEmail);
        Product product = productService.findProductById(request.getProductId());

        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            cartItemRepository.save(item);
            log.info("Quantity updated for product {} in cart of {}", product.getId(), userEmail);
        } else {
            CartItem newItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(product.getPrice())
                .build();
            cart.addItem(newItem);
            cartItemRepository.save(newItem);
            log.info("New product {} added to cart of {}", product.getId(), userEmail);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCartItem(String userEmail, Long productId, UpdateCartItemRequest request) {
        Cart cart = getOrCreateCart(userEmail);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
            .orElseThrow(() -> new RuntimeException("Product not found in cart."));

        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);

        log.info("Product {} quantity updated to {} in cart of {}", productId, request.getQuantity(), userEmail);
        return cartRepository.findByUserEmailWithItems(userEmail).orElse(cart);
    }

    @Override
    public Cart removeFromCart(String userEmail, Long productId) {
        Cart cart = getOrCreateCart(userEmail);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
            .orElseThrow(() -> new RuntimeException("Product not found in cart."));

        cart.removeItem(item);
        cartItemRepository.delete(item);

        log.info("Product {} removed from cart of {}", productId, userEmail);
        return cartRepository.findByUserEmailWithItems(userEmail).orElse(cart);
    }

    @Override
    public void clearCart(String userEmail) {
        Cart cart = cartRepository.findByUserEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("Cart not found."));

        cart.getItems().clear();
        cartRepository.save(cart);
        log.info("Cart cleared for user: {}", userEmail);
    }

    public Cart getCart(String userEmail) {
        return cartRepository.findByUserEmailWithItems(userEmail)
            .orElseThrow(() -> new RuntimeException("Cart not found."));
    }

    @Override
    public CartSummaryDto getCartSummary(String userEmail) {
        Cart cart = getOrCreateCart(userEmail);

        return CartSummaryDto.builder()
            .cartId(cart.getId())
            .userEmail(cart.getUserEmail())
            .totalItems(cart.getTotalItems())
            .totalPrice(cart.getTotalPrice())
            .uniqueProducts(cart.getItems().size())
            .build();
    }
}
