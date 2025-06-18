package com.architech.test.product.cart;

import com.architech.test.product.dto.AddToCartRequest;
import com.architech.test.product.dto.CartSummaryDto;
import com.architech.test.product.dto.UpdateCartItemRequest;
import com.architech.test.product.products.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartServiceImpl cartService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String userEmail = "admin@admin.com";

    @Test
    @WithMockUser(username = "admin@admin.com")
    void testGetCart() throws Exception {
        Cart cart = Cart.builder().id(1L).userEmail(userEmail).build();
        when(cartService.getCart(userEmail)).thenReturn(cart);

        mockMvc.perform(get("/api/cart")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Cart retrieved successfully."))
            .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    void testGetCartSummary() throws Exception {
        CartSummaryDto cartSummaryDTO = new CartSummaryDto();
        cartSummaryDTO.setTotalItems(3);

        when(cartService.getCartSummary(userEmail)).thenReturn(cartSummaryDTO);

        mockMvc.perform(get("/api/carts/summary")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Cart summary retrieved successfully."))
            .andExpect(jsonPath("$.data.totalItems").value(3));
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    void testAddToCart() throws Exception {
        AddToCartRequest request = new AddToCartRequest(1L, 2);
        Cart cart = Cart.builder().id(1L).userEmail(userEmail).build();

        when(cartService.addToCart(eq(userEmail), any(AddToCartRequest.class))).thenReturn(cart);

        mockMvc.perform(post("/api/cart/items")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message").value("Product added to cart successfully."));
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    void testUpdateCartItem() throws Exception {
        Long cartId = 1L;
        Long productId = 2L;

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserEmail(userEmail);

        Product product = new Product();
        product.setId(productId);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(5);
        cartItem.setCart(cart);

        cart.setItems(List.of(cartItem));

        UpdateCartItemRequest updateRequest = new UpdateCartItemRequest();
        updateRequest.setQuantity(5);

        when(cartService.getCartById(cartId)).thenReturn(cart);
        when(cartService.updateCartItem(eq(userEmail), eq(productId), any(UpdateCartItemRequest.class))).thenReturn(cart);

        mockMvc.perform(put("/api/carts/{cartId}/items", cartId)
                .with(csrf())
                .param("productId", productId.toString())
                .param("quantity", "5"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    void testRemoveFromCart() throws Exception {
        Long cartId = 1L;
        Long productId = 2L;
        String username = "admin@admin.com";

        Cart cart = new Cart();
        cart.setId(cartId);
        cart.setUserEmail(userEmail);
        cart.setItems(List.of());

        when(cartService.getCartById(cartId)).thenReturn(cart);
        when(cartService.removeFromCart(username, productId)).thenReturn(cart);

        mockMvc.perform(delete("/api/carts/{cartId}/items", cartId)
                .with(csrf())
                .param("productId", productId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$_id").value(cartId));
    }

    @Test
    @WithMockUser(username = "admin@admin.com")
    void testClearCart() throws Exception {
        // Create a cart that would be found
        Cart cart = Cart.builder()
            .id(1L)
            .userEmail(userEmail)
            .items(List.of())
            .build();

        // Mock the repository to return the cart when searched by email
        when(cartRepository.findByUserEmail(userEmail)).thenReturn(Optional.of(cart));

        // Mock the service clear method
        doNothing().when(cartService).clearCart(userEmail);

        mockMvc.perform(delete("/api/cart")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("Cart cleared successfully."));
    }
}
