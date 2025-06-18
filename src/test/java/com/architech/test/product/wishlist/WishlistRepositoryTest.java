package com.architech.test.product.wishlist;

import com.architech.test.product.users.User;
import com.architech.test.product.users.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishlistRepositoryTest {


    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateWishlist_withDefaults() {
        User user = User.builder()
            .email("test@admin.com")
            .password("secure")
            .firstname("Evan")
            .lastname("Jones")
            .build();

        user = userRepository.save(user);

        Wishlist wishlist = Wishlist.builder()
            .name("Favorites")
            .description("My favorite items")
            .user(user)
            .build();

        Wishlist saved = wishlistRepository.save(wishlist);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Favorites");
        assertThat(saved.getDescription()).isEqualTo("My favorite items");
        assertThat(saved.getIsDefault()).isFalse();
        assertThat(saved.getIsPublic()).isFalse();
        assertThat(saved.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(saved.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        assertThat(saved.getUser()).isEqualTo(user);
        assertThat(saved.getItems()).isNullOrEmpty();
    }
}
