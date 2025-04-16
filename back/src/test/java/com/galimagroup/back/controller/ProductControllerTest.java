package com.galimagroup.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galimagroup.back.dto.ProductDto;
import com.galimagroup.back.security.JwtTokenProvider;
import com.galimagroup.back.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.galimagroup.back.config.TestSecurityConfig;
import org.springframework.context.annotation.Import;

@WebMvcTest(ProductController.class)
@Import(TestSecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto creerProduitTest() {
        return ProductDto.builder()
                .code("P123")
                .name("Produit Test")
                .description("Description du produit")
                .image("image.jpg")
                .category("Catégorie Test")
                .price(20.99)
                .quantity(50)
                .internalReference("REF123")
                .shellId(1L)
                .inventoryStatus("In Stock")
                .rating(5)
                .createdAt(LocalDateTime.of(2023, 5, 10, 9, 45, 0, 0)) // Exemple de date fixe pour createdAt
                .updatedAt(LocalDateTime.of(2023, 5, 10, 9, 45, 0, 0)) // Exemple de date fixe pour updatedAt
                .build();
    }

    @Test
    void creerProduit_avecTokenAdminValide_retourneStatusCree() throws Exception {
        ProductDto produit = creerProduitTest();
        produit.setId(null);

        ProductDto produitCree = creerProduitTest();
        produitCree.setId(1L);

        when(jwtTokenProvider.isAdmin(anyString())).thenReturn(true);
        when(productService.createProduct(any(ProductDto.class)))
                .thenReturn(produitCree);

        mockMvc.perform(post("/api/products")
                        .header("Authorization", "Bearer faketoken.jwt.test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produit)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void recupererProduit_parId_retourneProduit() throws Exception {
        ProductDto produit = creerProduitTest();
        produit.setId(1L);
        when(productService.getProduct(1L)).thenReturn(produit);

        mockMvc.perform(get("/api/products/1")
                        .header("Authorization", "Bearer faketoken.jwt.test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void recupererTousLesProduits_retourneListe() throws Exception {
        List<ProductDto> produits = Arrays.asList(creerProduitTest());

        when(productService.getAllProducts()).thenReturn(produits);

        mockMvc.perform(get("/api/products")
                        .header("Authorization", "Bearer faketoken.jwt.test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void mettreAJourProduit_parId_retourneProduitMisAJour() throws Exception {
        ProductDto produitModifie = creerProduitTest();
        produitModifie.setName("Produit Modifié");

        when(jwtTokenProvider.isAdmin(anyString())).thenReturn(true);
        when(productService.updateProduct(eq(1L), any(ProductDto.class)))
                .thenReturn(produitModifie);

        mockMvc.perform(patch("/api/products/1")
                        .header("Authorization", "Bearer faketoken.jwt.test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produitModifie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Produit Modifié"));
    }

    @Test
    void supprimerProduit_parId_retourneStatusOk() throws Exception {
        when(jwtTokenProvider.isAdmin(anyString())).thenReturn(true);
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1")
                        .header("Authorization", "Bearer faketoken.jwt.test"))
                .andExpect(status().isOk()); // Le contrôleur retourne 200 OK, pas 204 No Content
    }
}
