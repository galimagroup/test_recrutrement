package sow.issa.recrutement.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sow.issa.recrutement.models.response.ProductResponse;
import sow.issa.recrutement.services.WishlistService;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<String> addProductToWishlist(@RequestBody Long productId) {
        wishlistService.addProductToWishlist(productId);
        return ResponseEntity.ok("Ajout réussi");
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<ProductResponse>> readAllItem() {
        return ResponseEntity.ok(wishlistService.readAllItem());
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Long productId) {
        wishlistService.removeProducToWishlist(productId);
        return ResponseEntity.ok("Suppression réussi");
    }
}
