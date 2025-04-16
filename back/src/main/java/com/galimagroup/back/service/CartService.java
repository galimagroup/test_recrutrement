package com.galimagroup.back.service;

import com.galimagroup.back.dto.CartDto;
import com.galimagroup.back.dto.CartItemDto;
import com.galimagroup.back.dto.ProductDto;
import com.galimagroup.back.model.Cart;
import com.galimagroup.back.model.CartItem;
import com.galimagroup.back.model.Product;
import com.galimagroup.back.model.Users;
import com.galimagroup.back.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public CartDto getCartForUser(Users user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        return convertToDto(cart);
    }

    @Transactional
    public CartDto addToCart(Users user, Long productId, Integer quantity) {
        Cart cart = cartRepository.findByUser(user).orElse(new Cart());
        cart.setUser(user);

        Product product = productService.getProductEntity(productId);
        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(quantity);

        cart.getItems().add(item);
        cart.setTotal(calculateTotal(cart));
        return convertToDto(cartRepository.save(cart));
    }

    @Transactional
    public CartDto removeFromCart(Users user, Long productId) {
        Cart cart = cartRepository.findByUser(user).orElseThrow();
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cart.setTotal(calculateTotal(cart));
        return convertToDto(cartRepository.save(cart));
    }

    private Double calculateTotal(Cart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    private CartDto convertToDto(Cart cart) {
        CartDto dto = modelMapper.map(cart, CartDto.class);
        dto.setItems(cart.getItems().stream().map(item -> {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProduct().getId());
            itemDto.setQuantity(item.getQuantity());
            return itemDto;
        }).collect(Collectors.toList()));
        dto.setUserId(cart.getUser().getId());
        return dto;
    }
}
