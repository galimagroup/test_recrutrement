package start.amdev.test.web.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import start.amdev.test.application.useCase.interfaces.IProduit;
import start.amdev.test.web.dtos.requests.PanierRequestDTO;
import start.amdev.test.web.dtos.requests.ProduitRequestDTO;
import start.amdev.test.web.dtos.responses.Response;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
//@SecurityRequirement(name = "Bearer-Authentication")
@Tag(name = "GestionProduitController", description = "Permet de gerer les produits")
public class ProduitController {
    private final IProduit iProduit;


    @PostMapping("/produit")
    public Response<Object> saveProduit(@Valid @RequestBody ProduitRequestDTO dto, HttpServletRequest request) {
        String userId = (String) (request.getAttribute("user_id") == null ? "" : request.getAttribute("user_id"));
        return iProduit.saveProduit(dto, userId);
    }
    @GetMapping("/produit/{id}")
    public Response<Object> produitByCode(@PathVariable("id") final String id) {
        return iProduit.produitByCode(Long.valueOf(id));
    }
    @PostMapping("/panier")
    public Response<Object>  ajouterProduitAuPanier(@RequestBody PanierRequestDTO panierRequestDTO) {
        return iProduit.ajouterProduitAuPanier(panierRequestDTO);
    }

}
