package com.galimagroup.gestionproduit.service.serviceImp;

import com.galimagroup.gestionproduit.dto.ProduitDto;
import com.galimagroup.gestionproduit.entity.Produit;
import com.galimagroup.gestionproduit.entity.User;
import com.galimagroup.gestionproduit.repo.ProduitRepository;
import com.galimagroup.gestionproduit.service.serviceInterface.ProduitInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class ProduitServiceImpl implements ProduitInterface {
    private  final ProduitRepository produitRepository;
    private  final UserServiceImpl userService;
    private final Logger log = LoggerFactory.getLogger(ProduitServiceImpl.class);

    public ProduitServiceImpl(ProduitRepository produitRepository, UserServiceImpl userService) {
        this.produitRepository = produitRepository;
        this.userService = userService;
    }
    private boolean isAdmin() {
        // Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.debug("isAdmin");
        log.debug(username);


        // Vérifier si l'email correspond à l'admin
        User user = userService.findUserByEmail(username);
        return !userService.isAdmin(user);
    }
    @Override
    public ProduitDto saveProduitDto(ProduitDto produitDto) {
        if (isAdmin()) {
            throw new SecurityException("Seul l'administrateur peut ajouter des produits.");
        }
        Produit  produit = produitDto.recupererProduit();
        Produit newproduit = produitRepository.save(produit);
        return new ProduitDto(newproduit);
    }

    @Override
    public Page<ProduitDto> findAllProduit(Pageable pageable) {
        return produitRepository.findAll(pageable).map(ProduitDto::new);
    }

    @Override
    public ProduitDto findProduitById(Long id) {
        Optional<Produit> produitOptional = produitRepository.findById(id);
        if (produitOptional.isEmpty()) {
            throw new RuntimeException("Produit avec ID " + id + " non trouvé !");
        }
        return new ProduitDto(produitOptional.get());    }

    @Override
    public ProduitDto updateProduit(Long id, ProduitDto produitDto) {
        if (isAdmin()) {
            throw new SecurityException("Seul l'administrateur peut ajouter des produits.");
        }
// Vérifier si le produit existe
        Optional<Produit> produitOptional = produitRepository.findById(id);
        if (produitOptional.isEmpty()) {
            throw new RuntimeException("Produit avec ID " + id + " non trouvé !");
        }

        // Mettre à jour les champs du produit
        Produit produit = produitOptional.get();
        produit.setCode(produitDto.getCode());
        produit.setDescription(produitDto.getDescription());
        produit.setPrice(produitDto.getPrice());
        produit.setRating(produitDto.getRating());

        // Enregistrer les modifications
        Produit updatedProduit = produitRepository.save(produit);
        return new ProduitDto(updatedProduit);    }

    @Override
    public void deleteProduit(Long id) {
        if (isAdmin()) {
            throw new SecurityException("Seul l'administrateur peut ajouter des produits.");
        }
        // Vérifier si le produit existe
        Optional<Produit> produitOptional = produitRepository.findById(id);
        if (produitOptional.isEmpty()) {
            throw new RuntimeException("Produit avec ID " + id + " non trouvé !");
        }

        // Supprimer le produit
        produitRepository.deleteById(id);
    }

}
