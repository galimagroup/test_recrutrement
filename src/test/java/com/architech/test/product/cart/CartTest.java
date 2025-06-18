package com.architech.test.product.cart;

import com.architech.test.product.products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    private Cart cart;

    @BeforeEach
    void setUp() {
        cart = Cart.builder()
            .userEmail("test@admin.com")
            .items(new ArrayList<>())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    @Test
    void testAddItem() {
        CartItem item = CartItem.builder()
            .id(1L)
            .product(Product.builder().id(100L).build())
            .quantity(2)
            .unitPrice(new BigDecimal("10.00"))
            .build();

        cart.addItem(item);

        assertEquals(1, cart.getItems().size());
        assertEquals(cart, item.getCart());
    }

    @Test
    void testRemoveItem() {
        CartItem item = CartItem.builder()
            .id(1L)
            .product(Product.builder().id(100L).build())
            .quantity(1)
            .unitPrice(new BigDecimal("20.00"))
            .build();

        cart.addItem(item);
        cart.removeItem(item);

        assertEquals(0, cart.getItems().size());
        assertNull(item.getCart());
    }

    @Test
    void testGetTotalItems() {
        CartItem item1 = CartItem.builder()
            .product(Product.builder().id(1L).build())
            .quantity(2)
            .unitPrice(new BigDecimal("5.00"))
            .build();

        CartItem item2 = CartItem.builder()
            .product(Product.builder().id(2L).build())
            .quantity(3)
            .unitPrice(new BigDecimal("7.00"))
            .build();

        cart.addItem(item1);
        cart.addItem(item2);

        assertEquals(5, cart.getTotalItems());
    }

    @Test
    void testGetTotalPrice() {
        CartItem item1 = CartItem.builder()
            .product(Product.builder().id(1L).build())
            .quantity(2)
            .unitPrice(new BigDecimal("5.00"))
            .build();

        CartItem item2 = CartItem.builder()
            .product(Product.builder().id(2L).build())
            .quantity(1)
            .unitPrice(new BigDecimal("7.50"))
            .build();

        cart.addItem(item1);
        cart.addItem(item2);

        assertEquals(new BigDecimal("17.50"), cart.getTotalPrice());
    }

    @Test
    void testOnCreateSetsTimestamps() {
        Cart newCart = new Cart();
        newCart.onCreate();

        assertNotNull(newCart.getCreatedAt());
        assertNotNull(newCart.getUpdatedAt());
    }

    @Test
    void testOnUpdateSetsUpdatedTimestamp() {
        Cart updatedCart = new Cart();
        updatedCart.onCreate();

        LocalDateTime createdAt = updatedCart.getCreatedAt();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}

        updatedCart.onUpdate();

        assertTrue(updatedCart.getUpdatedAt().isAfter(createdAt));
    }
}
