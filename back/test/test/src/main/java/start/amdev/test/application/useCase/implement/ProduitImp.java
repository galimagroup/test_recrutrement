package start.amdev.test.application.useCase.implement;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import start.amdev.test.Mapper.Produit.PanierMapper;
import start.amdev.test.Mapper.Produit.ProduitMapper;
import start.amdev.test.application.useCase.interfaces.IProduit;
import start.amdev.test.domain.Model.Panier;
import start.amdev.test.domain.Model.PanierProduit;
import start.amdev.test.domain.Model.Produit;
import start.amdev.test.domain.repository.PanierRepository;
import start.amdev.test.domain.repository.ProduitRepository;
import start.amdev.test.web.dtos.requests.PanierProduitRequestDTO;
import start.amdev.test.web.dtos.requests.PanierRequestDTO;
import start.amdev.test.web.dtos.requests.ProduitRequestDTO;
import start.amdev.test.web.dtos.responses.PanierResponseDTO;
import start.amdev.test.web.dtos.responses.Response;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitImp implements IProduit {
    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    private PanierMapper panierMapper;

    private PanierRepository panierRepository;


    @Override
    public Response<Object> saveProduit(ProduitRequestDTO produitRequestDTO, String id) {
        Produit produit = produitMapper.toEntity(produitRequestDTO);
        produitRepository.saveAndFlush(produit);
        return Response.ok().setPayload(produitMapper.toDto(produit)).setMessage("produit enregistré");
    }

    @Override
    public Response<Object> produitByCode(Long id) {
        Produit produit = (Produit) produitRepository.findById(id).orElse(null);
        return Response.ok().setPayload(produitMapper.toDto(produit)).setMessage("produit :" + id);
    }

    @Override
    @Transactional
    public Response<Object> ajouterProduitAuPanier(PanierRequestDTO panierRequestDTO) {
        // Créer un panier à partir du DTO
        Panier panier = new Panier();
        panierMapper.updateEntityFromDto(panierRequestDTO, panier);

        // Initialiser la liste des produits du panier et la variable total
        List<PanierProduit> panierProduits = new ArrayList<>();
        long total = 0;

        // Parcourir la liste des produits dans le panierRequestDTO
        for (PanierProduitRequestDTO panierProduitRequestDTO : panierRequestDTO.getProduits()) {
            // Récupérer le produit à partir du produitRepository
            Produit produit = produitRepository.findById(panierProduitRequestDTO.getProduitId())
                    .orElseThrow(() -> new RuntimeException("Produit avec ID " + panierProduitRequestDTO.getProduitId() + " non trouvé"));

            // Créer un objet PanierProduit et le lier au panier
            PanierProduit panierProduit = new PanierProduit();
            panierProduit.setProduit(produit);
            panierProduit.setQuantite(panierProduitRequestDTO.getQuantite());
            //panierProduit.setPrix(produit.getPrice());

            // Calculer le total du panier
            total += produit.getPrice() * panierProduit.getQuantite();

            // Lier le PanierProduit au panier
            panierProduit.setPanier(panier);

            // Ajouter le produit à la liste des produits du panier
            panierProduits.add(panierProduit);
        }

        // Assigner la liste des produits au panier et mettre à jour le total
        panier.setProduits(panierProduits);
        panier.setTotal(total);

        // Sauvegarder le panier dans la base de données
        panierRepository.save(panier);

        // Retourner la réponse avec un message et le DTO du panier
        return Response.ok()
                .setPayload(panierMapper.toDto(panier))  // Mapper le panier en DTO pour la réponse
                .setMessage("Produits ajoutés au panier avec succès");
    }



}
