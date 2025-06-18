package com.architech.test.product.cart;

import com.architech.test.product.dto.AddToCartRequest;
import com.architech.test.product.dto.CartSummaryDto;
import com.architech.test.product.dto.UpdateCartItemRequest;
import com.architech.test.product.products.Product;
import com.architech.test.product.products.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private CartServiceImpl cartService;

    private AutoCloseable closeable;

    private final String userEmail = "test@example.com";
    private Cart cart;
    private Product product;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        product = Product.builder()
            .id(1L)
            .name("Test Product")
            .price(new BigDecimal("100.00"))
            .build();

        cart = Cart.builder()
            .id(1L)
            .userEmail(userEmail)
            .items(new ArrayList<>()) // Important: mutable list
            .build();
    }

    @Test
    void testGetOrCreateCart_NewCart() {
        when(cartRepository.findByUserEmailWithItems(userEmail)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.getOrCreateCart(userEmail);

        assertNotNull(result);
        assertEquals(userEmail, result.getUserEmail());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void testAddToCart_NewItem() {
        AddToCartRequest request = new AddToCartRequest();
        request.setProductId(1L);
        request.setQuantity(2);

        when(cartRepository.findByUserEmailWithItems(userEmail)).thenReturn(Optional.of(cart));
        when(productService.findProductById(1L)).thenReturn(product);
        when(cartItemRepository.findByCartIdAndProductId(1L, 1L)).thenReturn(Optional.empty());
        when(cartItemRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(cartRepository.save(any())).thenReturn(cart);

        Cart result = cartService.addToCart(userEmail, request);

        assertEquals(1, result.getItems().size());
        verify(cartItemRepository).save(any(CartItem.class));
    }

    @Test
    void testAddToCart_ExistingItem() {
        CartItem existingItem = CartItem.builder()
            .id(10L)
            .product(product)
            .quantity(2)
            .unitPrice(product.getPrice())
            .cart(cart)
            .build();

        cart.getItems().add(existingItem);

        AddToCartRequest request = new AddToCartRequest();
        request.setProductId(1L);
        request.setQuantity(3);

        when(cartRepository.findByUserEmailWithItems(userEmail)).thenReturn(Optional.of(cart));
        when(productService.findProductById(1L)).thenReturn(product);
        when(cartItemRepository.findByCartIdAndProductId(1L, 1L)).thenReturn(Optional.of(existingItem));

        Cart result = cartService.addToCart(userEmail, request);

        assertEquals(5, existingItem.getQuantity());
        verify(cartItemRepository).save(existingItem);
    }

    @Test
    void testUpdateCartItem() {
        CartItem item = CartItem.builder()
            .id(1L)
            .product(product)
            .quantity(1)
            .unitPrice(product.getPrice())
            .cart(cart)
            .build();
        cart.getItems().add(item);

        UpdateCartItemRequest request = new UpdateCartItemRequest();
        request.setQuantity(5);

        when(cartRepository.findByUserEmailWithItems(userEmail)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(1L, 1L)).thenReturn(Optional.of(item));

        Cart result = cartService.updateCartItem(userEmail, 1L, request);

        assertEquals(5, item.getQuantity());
        verify(cartItemRepository).save(item);
    }

    @Test
    void testRemoveFromCart() {
        CartItem item = CartItem.builder()
            .id(1L)
            .product(product)
            .quantity(2)
            .unitPrice(product.getPrice())
            .cart(cart)
            .build();
        cart.getItems().add(item);

        when(cartRepository.findByUserEmailWithItems(userEmail)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByCartIdAndProductId(1L, 1L)).thenReturn(Optional.of(item));

        Cart result = cartService.removeFromCart(userEmail, 1L);

        assertTrue(result.getItems().isEmpty());
        verify(cartItemRepository).delete(item);
    }

    @Test
    void testClearCart() {
        CartItem item1 = CartItem.builder().id(1L).product(product).quantity(1).unitPrice(product.getPrice()).cart(cart).build();
        cart.getItems().add(item1);

        when(cartRepository.findByUserEmail(userEmail)).thenReturn(Optional.of(cart));

        cartService.clearCart(userEmail);

        assertTrue(cart.getItems().isEmpty());
        verify(cartRepository).save(cart);
    }

    @Test
    void testGetCartSummary() {
        CartItem item1 = CartItem.builder().product(product).quantity(2).unitPrice(product.getPrice()).build();
        cart.getItems().add(item1);

        when(cartRepository.findByUserEmailWithItems(userEmail)).thenReturn(Optional.of(cart));

        CartSummaryDto summary = cartService.getCartSummary(userEmail);

        assertEquals(cart.getUserEmail(), summary.getUserEmail());
        assertEquals(cart.getId(), summary.getCartId());
        assertEquals(2, summary.getTotalItems());
        assertEquals(1, summary.getUniqueProducts());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}
