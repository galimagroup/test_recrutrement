package com.galimagroup.gestionproduit.service.serviceImp;

import com.galimagroup.gestionproduit.entity.Panier;
import com.galimagroup.gestionproduit.entity.Produit;
import com.galimagroup.gestionproduit.entity.Souhait;
import com.galimagroup.gestionproduit.entity.User;
import com.galimagroup.gestionproduit.repo.PanierRepository;
import com.galimagroup.gestionproduit.repo.ProduitRepository;
import com.galimagroup.gestionproduit.repo.SouhaitRepository;
import com.galimagroup.gestionproduit.repo.UtilisateurRepository;
import com.galimagroup.gestionproduit.service.serviceInterface.SouhaitServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class SouhaitServiceImpl implements SouhaitServiceInterface {
    private final SouhaitRepository souhaitRepository;
    private final ProduitRepository produitRepository;
    private final UtilisateurRepository userRepository;

    public SouhaitServiceImpl(SouhaitRepository souhaitRepository, ProduitRepository produitRepository, UtilisateurRepository userRepository) {
        this.souhaitRepository = souhaitRepository;
        this.produitRepository = produitRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void ajoutProduitToSouhait(Long userId, Long productId) {
        Souhait souhait = getOrCreateCart(userId);
        Produit product = produitRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        souhait.getProducts().add(product);
        souhaitRepository.save(souhait);
    }

    @Override
    public void supprimerProduitToSouhait(Long userId, Long productId) {

    }

    @Override
    public Souhait getSouhaitByUserId(Long userId) {

        return souhaitRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
    private Souhait getOrCreateCart(Long userId) {
        return souhaitRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    Souhait souhait = new Souhait();
                    souhait.setUser(user);
                    return souhaitRepository.save(souhait);
                });
    }
}
