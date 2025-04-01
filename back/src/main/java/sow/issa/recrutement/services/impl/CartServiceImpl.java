package sow.issa.recrutement.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sow.issa.recrutement.exceptions.ResourceNotFoundException;
import sow.issa.recrutement.mappers.CartItemMapper;
import sow.issa.recrutement.models.request.CartItemRequest;
import sow.issa.recrutement.models.response.CartResponse;
import sow.issa.recrutement.repositories.CartItemRepository;
import sow.issa.recrutement.repositories.CartRepository;
import sow.issa.recrutement.repositories.ProductRepository;
import sow.issa.recrutement.repositories.UserRepository;
import sow.issa.recrutement.security.SecurityUtils;
import sow.issa.recrutement.services.CartService;
import sow.issa.recrutement.services.ProductService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final CartItemMapper cartItemMapper;
    private static final String USER_NOT_FOUND = "utilisateur introuvable dans la base de donnée";

    @Override
    public CartResponse addProductToCart(CartItemRequest request) {
        var user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        var product = productService.readProduct(request.getProductId());

        var cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(""));

        var cartItem = cartItemMapper.asEntity(request);

        cartItem.setCart(cart);
        cartItem.setProduct(productRepository.findById(product.getId()).get());
        cartItem.setPrice(product.getPrice() * request.getQuantity());

        var savedItem = cartItemRepository.save(cartItem);

        var totalCart = Objects.nonNull(cart.getTotalPrice()) ? cart.getTotalPrice() + savedItem.getPrice() : savedItem.getPrice();

        cart.setTotalPrice(totalCart);

        cartRepository.save(cart);

        log.info("Item {} successfully added", savedItem.getId());

        return CartResponse.builder()
                .items(cartItemMapper.parse(List.of(savedItem)))
                .TotalCart(cart.getTotalPrice())
                .build();
    }

    @Override
    public CartResponse readAllItem() {
        var user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        var items = cartItemRepository.findByCartId(user.getCart().getId());

        return CartResponse.builder()
                .items(cartItemMapper.parse(items))
                .TotalCart(user.getCart().getTotalPrice())
                .build();
    }

    @Override
    public void removeCartItem(Long cartItemId) {
        if(Objects.isNull(cartItemId)) {
            throw new RuntimeException("L'id ne doit pas être null");
        }

        var item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("La ligne n'existe pas!", cartItemId)));

        var user = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));

        var totalUserCart = user.getCart().getTotalPrice() - (item.getQuantity() * item.getProduct().getPrice());

        user.getCart().setTotalPrice(totalUserCart);

        cartRepository.save(user.getCart());

        cartItemRepository.deleteById(cartItemId);

        log.info("Item {} successfully deleted", cartItemId);
    }
}
