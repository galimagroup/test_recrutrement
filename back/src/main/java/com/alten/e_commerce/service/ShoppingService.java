package com.alten.e_commerce.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.alten.e_commerce.entity.Product;
import com.alten.e_commerce.entity.ShoppingCart;
import com.alten.e_commerce.entity.ShoppingCartKey;
import com.alten.e_commerce.entity.User;
import com.alten.e_commerce.interfaces.IShopping;
import com.alten.e_commerce.repository.ProductRepository;
import com.alten.e_commerce.repository.ShoppingCartRepository;
import com.alten.e_commerce.repository.UserRepository;

@Service
public class ShoppingService implements IShopping{
    
    final ProductRepository productRepository;
    final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    ShoppingService(ProductRepository productRepository,UserRepository userRepository,ShoppingCartRepository shoppingCartRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void like(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new RuntimeException("Product not found")
        );
        
        User user = this.currentUser();
        Set<User> users = product.getLikes();
        users.add(user);
        product.setLikes(users);

        Set<Product> products = user.getLikedProducts();
        products.add(product);
        user.setLikedProducts(products);
        userRepository.save(user);
    }
    @Override
    public void dislike(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new RuntimeException("Product not found")
        );
        
        User user = this.currentUser();
        Set<User> users = product.getLikes();
        users.remove(user);
        product.setLikes(users);

        Set<Product> products = user.getLikedProducts();
        products.remove(product);
        user.setLikedProducts(products);
        userRepository.save(user);
    }
    @Override
    public ShoppingCart addToCart(ShoppingCart shoppingCart){
        User user = this.currentUser();
        ShoppingCartKey key = ShoppingCartKey.builder().productId(shoppingCart.getProduct().getId())
        .userId(user.getId()).build();
        shoppingCart.setId(key);
        Optional<ShoppingCart> shoppingCart2 = shoppingCartRepository.findById(shoppingCart.getId());

        if (shoppingCart2.isPresent()) {
            shoppingCart2.get().setQuantity(shoppingCart.getQuantity());
            return shoppingCartRepository.save(shoppingCart2.get());
        } else {
            return shoppingCartRepository.save(shoppingCart);
        }
    }
    @Override
    public void removeToCart(ShoppingCart shoppingCart){
        User user = this.currentUser();
        ShoppingCartKey key = ShoppingCartKey.builder().productId(shoppingCart.getProduct().getId())
        .userId(user.getId()).build();
        shoppingCart.setId(key);
        Optional<ShoppingCart> shoppingCart2 = shoppingCartRepository.findById(shoppingCart.getId());

        if (shoppingCart2.isPresent()) {
            shoppingCartRepository.delete(shoppingCart2.get());
        }
    }
    @Override
    public List<ShoppingCart> myCarts(){
        User user = this.currentUser();
        List<ShoppingCart> carts = shoppingCartRepository.findAll().stream().filter(cart -> cart.getUser().getId().equals(user.getId())).collect(Collectors.toList());
        return carts;
    }
    @Override
    public Set<Product> myFavoriteProducts(){
        User user = this.currentUser();
        return user.getLikedProducts();
    }

    private User currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((User) auth.getPrincipal());
        return user;
    }
}
