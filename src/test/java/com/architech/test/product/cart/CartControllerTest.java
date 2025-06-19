package com.architech.test.product.cart;

import com.architech.test.product.dto.AddToCartRequest;
import com.architech.test.product.dto.CartSummaryDto;
import com.architech.test.product.dto.UpdateCartItemRequest;
import com.architech.test.product.utils.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartServiceImpl cartService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CartController cartController;

    private final String TEST_USER_EMAIL = "test@example.com";

    @BeforeEach
    void setUp() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(TEST_USER_EMAIL);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getCart_ShouldReturnCartSuccessfully() {
        // Arrange
        Cart mockCart = new Cart();
        when(cartService.getCart(TEST_USER_EMAIL)).thenReturn(mockCart);

        // Act
        ResponseEntity<?> response = cartController.getCart();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Response responseBody = (Response) response.getBody();
        assertEquals("Cart retrieved successfully.", responseBody.getMessage());
        assertEquals(mockCart, responseBody.getData());
        verify(cartService, times(1)).getCart(TEST_USER_EMAIL);
    }

    @Test
    void getCartSummary_ShouldReturnSummarySuccessfully() {
        // Arrange
        CartSummaryDto mockSummary = new CartSummaryDto();
        when(cartService.getCartSummary(TEST_USER_EMAIL)).thenReturn(mockSummary);

        // Act
        ResponseEntity<?> response = cartController.getCartSummary();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Response responseBody = (Response) response.getBody();
        assertEquals("Cart summary retrieved successfully.", responseBody.getMessage());
        assertEquals(mockSummary, responseBody.getData());
        verify(cartService, times(1)).getCartSummary(TEST_USER_EMAIL);
    }

    @Test
    void addToCart_ShouldAddProductSuccessfully() {
        // Arrange
        AddToCartRequest request = new AddToCartRequest(1L, 2);
        Cart mockCart = new Cart();
        when(cartService.addToCart(TEST_USER_EMAIL, request)).thenReturn(mockCart);

        // Act
        ResponseEntity<?> response = cartController.addToCart(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Response responseBody = (Response) response.getBody();
        assertEquals("Product added to cart successfully.", responseBody.getMessage());
        assertEquals(mockCart, responseBody.getData());
        assertEquals(HttpStatus.CREATED, responseBody.getStatus());
        verify(cartService, times(1)).addToCart(TEST_USER_EMAIL, request);
    }

    @Test
    void updateCartItem_ShouldUpdateQuantitySuccessfully() {
        // Arrange
        Long productId = 1L;
        UpdateCartItemRequest request = new UpdateCartItemRequest(3);
        Cart mockCart = new Cart();
        when(cartService.updateCartItem(TEST_USER_EMAIL, productId, request)).thenReturn(mockCart);

        // Act
        ResponseEntity<?> response = cartController.updateCartItem(productId, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Response responseBody = (Response) response.getBody();
        assertEquals("Quantity updated successfully.", responseBody.getMessage());
        assertEquals(mockCart, responseBody.getData());
        verify(cartService, times(1)).updateCartItem(TEST_USER_EMAIL, productId, request);
    }

    @Test
    void removeFromCart_ShouldRemoveProductSuccessfully() {
        // Arrange
        Long productId = 1L;
        Cart mockCart = new Cart();
        when(cartService.removeFromCart(TEST_USER_EMAIL, productId)).thenReturn(mockCart);

        // Act
        ResponseEntity<?> response = cartController.removeFromCart(productId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Response responseBody = (Response) response.getBody();
        assertEquals("Product removed from cart successfully.", responseBody.getMessage());
        assertEquals(mockCart, responseBody.getData());
        verify(cartService, times(1)).removeFromCart(TEST_USER_EMAIL, productId);
    }

    @Test
    void clearCart_ShouldClearCartSuccessfully() {
        // Arrange
        doNothing().when(cartService).clearCart(TEST_USER_EMAIL);

        // Act
        ResponseEntity<?> response = cartController.clearCart();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Response responseBody = (Response) response.getBody();
        assertEquals("Cart cleared successfully.", responseBody.getMessage());
        assertNull(responseBody.getData());
        verify(cartService, times(1)).clearCart(TEST_USER_EMAIL);
    }
}
