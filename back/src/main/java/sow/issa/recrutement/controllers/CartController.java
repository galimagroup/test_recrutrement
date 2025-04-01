package sow.issa.recrutement.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sow.issa.recrutement.models.request.CartItemRequest;
import sow.issa.recrutement.models.response.CartResponse;
import sow.issa.recrutement.services.CartService;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<CartResponse> addProductToCart(@RequestBody @Valid CartItemRequest itemRequest) {
        return ResponseEntity.ok(cartService.addProductToCart(itemRequest));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<CartResponse> readAllItem() {
        return ResponseEntity.ok(cartService.readAllItem());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok().body("Suppression réussi");
    }
}
