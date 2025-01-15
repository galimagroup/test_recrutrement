package com.alten.e_commerce.interfaces;

import java.util.List;
import java.util.Set;

import com.alten.e_commerce.entity.Product;
import com.alten.e_commerce.entity.ShoppingCart;

public interface IShopping {
    
    void like(Long productId);

    void dislike(Long productId);

    ShoppingCart addToCart(ShoppingCart shoppingCart);

    void removeToCart(ShoppingCart shoppingCart);

    List<ShoppingCart> myCarts();

    Set<Product> myFavoriteProducts();
}
