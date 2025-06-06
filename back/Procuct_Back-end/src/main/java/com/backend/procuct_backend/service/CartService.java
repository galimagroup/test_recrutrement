package com.backend.procuct_backend.service;

import com.backend.procuct_backend.dao.*;
import com.backend.procuct_backend.dto.Cart;
import com.backend.procuct_backend.dto.CartItem;
import com.backend.procuct_backend.entitie.*;
import com.backend.procuct_backend.exception.EntityNotFoundException;
import com.backend.procuct_backend.exception.RequestException;
//import com.backend.procuct_backend.mapping.CartItemMapper;
import com.backend.procuct_backend.mapping.CartItemMapper;
import com.backend.procuct_backend.mapping.CartMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class CartService {
    private final ICartRepository cartRepository;
    private final ICartItemRepository cartItemRepository;
    private final IProductRepository productRepository;
    private final IUserRepository userRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final MessageSource messageSource;

    public CartService(ICartRepository cartRepository,
                       ICartItemRepository cartItemRepository,
                       IProductRepository productRepository,
                       IUserRepository userRepository,
                       CartMapper cartMapper,
                       CartItemMapper cartItemMapper,
                       MessageSource messageSource) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartMapper = cartMapper;
        this.cartItemMapper = cartItemMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public Cart getUserCart() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return cartRepository.findByUserEmail(email)
                .map(cartMapper::toCart)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("cart.notfound", null, null)
                ));
    }

    @Transactional
    public Cart addProductToCart(int productId, int quantity) {
        if (quantity <= 0) {
            throw new RequestException(
                    messageSource.getMessage("cart.quantity.invalid", null, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        CartEntity cart = cartRepository.findByUserEmail(email)
                .orElseGet(() -> createNewCart(email));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("product.notfound", new Object[]{productId}, Locale.getDefault())
                ));

        CartItemEntity cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElse(new CartItemEntity());

        if (cartItem.getId() == 0) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
        return cartMapper.toCart(cartRepository.findById(cart.getId()).get());
    }

    @Transactional
    public Cart updateCartItemQuantity(int productId, int quantity) {
        if (quantity <= 0) {
            throw new RequestException(
                    messageSource.getMessage("cart.quantity.invalid", null, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST
            );
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CartEntity cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("cart.notfound", null, Locale.getDefault())
                ));

        CartItemEntity cartItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("cart.item.notfound", new Object[]{productId}, Locale.getDefault())
                ));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cartMapper.toCart(cartRepository.findById(cart.getId()).get());
    }

    @Transactional
    public Cart removeProductFromCart(int productId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CartEntity cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("cart.notfound", null, Locale.getDefault())
                ));

        if (!cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).isPresent()) {
            throw new EntityNotFoundException(
                    messageSource.getMessage("cart.item.notfound", new Object[]{productId}, Locale.getDefault())
            );
        }

        cartItemRepository.deleteByCartIdAndProductId(cart.getId(), productId);
        return cartMapper.toCart(cartRepository.findById(cart.getId()).get());
    }

    @Transactional
    public void clearCart() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        CartEntity cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("cart.notfound", null, Locale.getDefault())
                ));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private CartEntity createNewCart(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("user.notfound", new Object[]{email}, Locale.getDefault())
                ));

        CartEntity cart = new CartEntity();
        cart.setUser(user);
        return cartRepository.save(cart);
    }
}