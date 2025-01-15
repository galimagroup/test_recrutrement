package start.amdev.test.application.useCase.interfaces;

import start.amdev.test.domain.Model.Produit;
import start.amdev.test.web.dtos.requests.PanierRequestDTO;
import start.amdev.test.web.dtos.requests.ProduitRequestDTO;
import start.amdev.test.web.dtos.responses.Response;

public interface IProduit {
    Response<Object> saveProduit(ProduitRequestDTO produitRequestDTO, String id);
    Response<Object> produitByCode(Long code);
    Response<Object> ajouterProduitAuPanier(PanierRequestDTO panierRequestDTO);
}
