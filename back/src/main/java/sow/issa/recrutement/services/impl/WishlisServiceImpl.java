package sow.issa.recrutement.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sow.issa.recrutement.exceptions.ResourceAlreadyExistException;
import sow.issa.recrutement.exceptions.ResourceNotFoundException;
import sow.issa.recrutement.mappers.ProductMapper;
import sow.issa.recrutement.models.response.ProductResponse;
import sow.issa.recrutement.repositories.ProductRepository;
import sow.issa.recrutement.repositories.UserRepository;
import sow.issa.recrutement.repositories.WishlistRepository;
import sow.issa.recrutement.security.SecurityUtils;
import sow.issa.recrutement.services.WishlistService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishlisServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private static final String USER_NOT_FOUND = "utilisateur introuvable dans la base de donnée";
    private static final String PRODUCT_NOT_FOUND = "Produit introuvable";

    @Override
    public void addProductToWishlist(Long productId) {
        var user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        var wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Liste envie de l'utilisateur"));
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));

        if (wishlist.getProducts().contains(product)) {
            throw new ResourceAlreadyExistException("Produit existe déja dans liste des envies de l'utilisateur");
        }

        wishlist.getProducts().add(product);

        var savedProduct = wishlistRepository.save(user.getWishlist());

        log.info("Item {} successfully added", savedProduct.getId());
    }

    @Override
    public List<ProductResponse> readAllItem() {
        var user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        return user.getWishlist()
                .getProducts()
                .stream().map(productMapper::asResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void removeProducToWishlist(Long productId) {
        var user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        var wishlist = wishlistRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Liste envie de l'utilisateur"));

        var  product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

        if (wishlist.getProducts().contains(product)) {
            wishlist.getProducts().remove(product);
            wishlistRepository.save(wishlist);
            log.info("Item {} successfully added", product.getId());
        } else {
            throw new ResourceNotFoundException("Produit n'existe pas dans la liste des envies de l'utilisateur");
        }
    }
}
