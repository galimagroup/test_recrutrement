package com.architech.test.product.cart;

import com.architech.test.product.products.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemTest {

    @Test
    void testGetTotalPrice() {
        CartItem item = CartItem.builder()
            .quantity(3)
            .unitPrice(new BigDecimal("19.99"))
            .build();

        BigDecimal expected = new BigDecimal("59.97");
        assertEquals(0, expected.compareTo(item.getTotalPrice()));
    }

    @Test
    void testOnCreateSetsAddedAt() {
        CartItem item = new CartItem();
        item.onCreate();
        assertNotNull(item.getAddedAt());
    }

    @Test
    void testLombokBuilderAndGetters() {
        Product product = Product.builder()
            .id(101L)
            .name("Test Product")
            .price(new BigDecimal("10.00"))
            .build();

        Cart cart = Cart.builder()
            .id(1L)
            .userEmail("user@example.com")
            .build();

        CartItem item = CartItem.builder()
            .id(10L)
            .cart(cart)
            .product(product)
            .quantity(2)
            .unitPrice(new BigDecimal("10.00"))
            .addedAt(LocalDateTime.now())
            .build();

        assertEquals(10L, item.getId());
        assertEquals(cart, item.getCart());
        assertEquals(product, item.getProduct());
        assertEquals(2, item.getQuantity());
        assertEquals(new BigDecimal("10.00"), item.getUnitPrice());
    }

    @Test
    void testSetCartNull() {
        CartItem item = new CartItem();
        item.setCart(null);
        assertNull(item.getCart());
    }

    @Test
    void testSetProduct() {
        Product product = Product.builder().id(1L).name("New").price(BigDecimal.TEN).build();
        CartItem item = new CartItem();
        item.setProduct(product);
        assertEquals(product, item.getProduct());
    }
}
