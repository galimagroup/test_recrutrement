package com.backend.procuct_backend.service;

import com.backend.procuct_backend.dao.*;
import com.backend.procuct_backend.dto.Wishlist;
import com.backend.procuct_backend.entitie.*;
import com.backend.procuct_backend.exception.EntityNotFoundException;
import com.backend.procuct_backend.exception.RequestException;
import com.backend.procuct_backend.mapping.WishlistMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class WishlistService {

    private final IWishlistRepository wishlistRepository;
    private final IWishlistItemRepository wishlistItemRepository;
    private final IProductRepository productRepository;
    private final IUserRepository userRepository;
    private final WishlistMapper wishlistMapper;
    private final MessageSource messageSource;

    public WishlistService(IWishlistRepository wishlistRepository,
                           IWishlistItemRepository wishlistItemRepository,
                           IProductRepository productRepository,
                           IUserRepository userRepository,
                           WishlistMapper wishlistMapper,
                           MessageSource messageSource) {
        this.wishlistRepository = wishlistRepository;
        this.wishlistItemRepository = wishlistItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.wishlistMapper = wishlistMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public Wishlist getUserWishlist() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return wishlistRepository.findByUserEmail(email)
                .map(wishlistMapper::toWishlist)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("wishlist.notfound", null, Locale.getDefault())
                ));
    }

    @Transactional
    public Wishlist addProductToWishlist(int productId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        WishlistEntity wishlist = wishlistRepository.findByUserEmail(email)
                .orElseGet(() -> createNewWishlist(email));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("product.notfound", new Object[]{productId}, Locale.getDefault())
                ));


        if (wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), productId)) {
            throw new RequestException(
                    messageSource.getMessage("wishlist.product.exists", new Object[]{productId}, Locale.getDefault()),
                    HttpStatus.CONFLICT
            );
        }

        WishlistItemEntity wishlistItem = new WishlistItemEntity();
        wishlistItem.setWishlist(wishlist);
        wishlistItem.setProduct(product);

        wishlistItemRepository.save(wishlistItem);
        return wishlistMapper.toWishlist(wishlistRepository.findById(wishlist.getId()).get());
    }

    @Transactional
    public Wishlist removeProductFromWishlist(int productId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WishlistEntity wishlist = wishlistRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("wishlist.notfound", null, Locale.getDefault())
                ));

        if (!wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), productId)) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("wishlist.item.notfound", new Object[]{productId}, Locale.getDefault())
            );
        }

        wishlistItemRepository.deleteByWishlistIdAndProductId(wishlist.getId(), productId);
        return wishlistMapper.toWishlist(wishlistRepository.findById(wishlist.getId()).get());
    }

    @Transactional
    public void clearWishlist() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WishlistEntity wishlist = wishlistRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("wishlist.notfound", null, Locale.getDefault())
                ));

        wishlist.getItems().clear();
        wishlistRepository.save(wishlist);
    }

    @Transactional(readOnly = true)
    public boolean isProductInWishlist(int productId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return wishlistRepository.findByUserEmail(email)
                .map(wishlist -> wishlistItemRepository.existsByWishlistIdAndProductId(wishlist.getId(), productId))
                .orElse(false);
    }

    private WishlistEntity createNewWishlist(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("user.notfound", new Object[]{email}, Locale.getDefault())
                ));

        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }
}