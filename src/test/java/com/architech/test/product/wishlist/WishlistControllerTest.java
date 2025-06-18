package com.architech.test.product.wishlist;

import com.architech.test.product.dto.AddProductToWishlistRequest;
import com.architech.test.product.dto.CreateWishlistRequest;
import com.architech.test.product.dto.WishlistItemResponseDTO;
import com.architech.test.product.dto.WishlistResponseDTO;
import com.architech.test.product.products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WishlistControllerTest {

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserWishlists() {
        Wishlist wishlist = Wishlist.builder()
            .id(1L)
            .name("My Wishlist")
            .description("A wishlist")
            .isPublic(false)
            .isDefault(false)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .items(Collections.emptyList())
            .build();

        when(wishlistService.getUserWishlists(1L)).thenReturn(List.of(wishlist));

        ResponseEntity<List<WishlistResponseDTO>> response = wishlistController.getUserWishlists(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("My Wishlist", response.getBody().get(0).getName());
    }

    @Test
    void testCreateWishlist() {
        CreateWishlistRequest request = new CreateWishlistRequest();
        request.setUserId(1L);
        request.setName("New Wishlist");
        request.setDescription("Description");
        request.setIsPublic(true);

        Wishlist wishlist = Wishlist.builder()
            .id(1L)
            .name("New Wishlist")
            .description("Description")
            .isPublic(true)
            .isDefault(false)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        when(wishlistService.createWishlist(1L, "New Wishlist", "Description", true)).thenReturn(wishlist);

        ResponseEntity<WishlistResponseDTO> response = wishlistController.createWishlist(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("New Wishlist", response.getBody().getName());
    }

    @Test
    void testAddProductToWishlist_success() {
        AddProductToWishlistRequest request = new AddProductToWishlistRequest();
        request.setProductId(2L);
        request.setNote("Test note");
        request.setPriority(1);

        doNothing().when(wishlistService).addProductToWishlist(1L, 2L, "Test note", 1);

        ResponseEntity<String> response = wishlistController.addProductToWishlist(1L, request);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product added to wishlist successfully.", response.getBody());
    }

    @Test
    void testAddProductToWishlist_alreadyExists() {
        AddProductToWishlistRequest request = new AddProductToWishlistRequest();
        request.setProductId(2L);
        request.setNote("Test");
        request.setPriority(1);

        doThrow(new IllegalArgumentException("The product is already in this wishlist"))
            .when(wishlistService).addProductToWishlist(1L, 2L, "Test", 1);

        ResponseEntity<String> response = wishlistController.addProductToWishlist(1L, request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("The product is already in this wishlist", response.getBody());
    }

    @Test
    void testRemoveProductFromWishlist() {
        doNothing().when(wishlistService).removeProductFromWishlist(1L, 2L);

        ResponseEntity<String> response = wishlistController.removeProductFromWishlist(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Product removed from wishlist successfully.", response.getBody());
    }

    @Test
    void testIsProductInWishlists() {
        when(wishlistService.isProductInUserWishlists(1L, 2L)).thenReturn(true);

        ResponseEntity<Boolean> response = wishlistController.isProductInWishlists(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody());
    }

    @Test
    void testGetWishlistItems() {
        WishlistItem item = WishlistItem.builder()
            .id(10L)
            .note("Important")
            .priority(2)
            .addedAt(LocalDateTime.now())
            .product(Product.builder().id(100L).name("Product 1").price(BigDecimal.valueOf(100.0)).build())
            .build();

        when(wishlistService.getWishlistItems(1L)).thenReturn(List.of(item));

        ResponseEntity<List<WishlistItemResponseDTO>> response = wishlistController.getWishlistItems(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Important", response.getBody().get(0).getNote());
    }
}
