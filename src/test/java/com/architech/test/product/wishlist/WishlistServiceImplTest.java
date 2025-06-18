package com.architech.test.product.wishlist;

import com.architech.test.product.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.mock;

public class WishlistServiceImplTest {

    private WishlistRepository wishlistRepository;
    private WishlistItemRepository wishlistItemRepository;
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    void setUp() {
        wishlistRepository = mock(WishlistRepository.class);
        wishlistItemRepository = mock(WishlistItemRepository.class);
        wishlistService = new WishlistServiceImpl(wishlistRepository, wishlistItemRepository);
    }

    @Test
    void createWishlist_shouldReturnSavedWishlist() {
        Wishlist wishlist = Wishlist.builder().name("Favorites").build();
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        Wishlist result = wishlistService.createWishlist(1L, "Favorites", "My favorite products", false);

        assertThat(result.getName()).isEqualTo("Favorites");
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    void getUserWishlists_shouldReturnWishlists() {
        when(wishlistRepository.findByUserIdOrderByCreatedAtDesc(1L))
            .thenReturn(List.of(Wishlist.builder().name("W1").build()));

        List<Wishlist> result = wishlistService.getUserWishlists(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("W1");
    }

    @Test
    void getDefaultWishlist_shouldReturnOptional() {
        Wishlist wishlist = Wishlist.builder().isDefault(true).build();
        when(wishlistRepository.findByUserIdAndIsDefaultTrue(1L)).thenReturn(Optional.of(wishlist));

        Optional<Wishlist> result = wishlistService.getDefaultWishlist(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getIsDefault()).isTrue();
    }

    @Test
    void getOrCreateDefaultWishlist_shouldReturnExisting() {
        Wishlist wishlist = Wishlist.builder().id(1L).isDefault(true).build();
        when(wishlistRepository.findByUserIdAndIsDefaultTrue(1L)).thenReturn(Optional.of(wishlist));

        Wishlist result = wishlistService.getOrCreateDefaultWishlist(1L);

        assertThat(result.getId()).isEqualTo(1L);
        verify(wishlistRepository, never()).save(any());
    }

    @Test
    void getOrCreateDefaultWishlist_shouldCreateIfNotExists() {
        when(wishlistRepository.findByUserIdAndIsDefaultTrue(1L)).thenReturn(Optional.empty());

        Wishlist toSave = Wishlist.builder()
            .name("My wishlist")
            .description("Default wishlist")
            .isDefault(true)
            .isPublic(false)
            .user(User.builder().id(1L).build())
            .build();

        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(toSave);

        Wishlist result = wishlistService.getOrCreateDefaultWishlist(1L);

        assertThat(result.getName()).isEqualTo("My wishlist");
        verify(wishlistRepository).save(any(Wishlist.class));
    }

    @Test
    void addProductToWishlist_shouldSaveWishlistItem() {
        when(wishlistItemRepository.findByWishlistIdAndProductId(1L, 10L))
            .thenReturn(Optional.empty());

        wishlistService.addProductToWishlist(1L, 10L, "Great product", 2);

        verify(wishlistItemRepository).save(any(WishlistItem.class));
    }

    @Test
    void addProductToWishlist_shouldThrowIfProductExists() {
        when(wishlistItemRepository.findByWishlistIdAndProductId(1L, 10L))
            .thenReturn(Optional.of(WishlistItem.builder().build()));

        assertThatThrownBy(() ->
            wishlistService.addProductToWishlist(1L, 10L, "Already added", 1)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("The product is already in this wishlist");
    }

    @Test
    void addProductToDefaultWishlist_shouldCallUnderlyingMethods() {
        Wishlist wishlist = Wishlist.builder().id(5L).isDefault(true).build();
        when(wishlistRepository.findByUserIdAndIsDefaultTrue(1L)).thenReturn(Optional.of(wishlist));

        wishlistService.addProductToDefaultWishlist(1L, 9L, "Add to default");

        verify(wishlistItemRepository).save(any(WishlistItem.class));
    }

    @Test
    void removeProductFromWishlist_shouldDeleteItem() {
        wishlistService.removeProductFromWishlist(1L, 10L);

        verify(wishlistItemRepository).deleteByWishlistIdAndProductId(1L, 10L);
    }

    @Test
    void getWishlistItems_shouldReturnList() {
        when(wishlistItemRepository.findByWishlistIdOrderByAddedAtDesc(1L))
            .thenReturn(Collections.singletonList(WishlistItem.builder().id(99L).build()));

        List<WishlistItem> result = wishlistService.getWishlistItems(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(99L);
    }

    @Test
    void isProductInUserWishlists_shouldReturnTrueIfFound() {
        when(wishlistItemRepository.findByUserIdAndProductId(1L, 20L))
            .thenReturn(List.of(WishlistItem.builder().build()));

        boolean result = wishlistService.isProductInUserWishlists(1L, 20L);

        assertThat(result).isTrue();
    }

    @Test
    void getProductWishlistCount_shouldReturnCount() {
        when(wishlistItemRepository.countByProductId(5L)).thenReturn(3L);

        Long result = wishlistService.getProductWishlistCount(5L);

        assertThat(result).isEqualTo(3L);
    }
}
