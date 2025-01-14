package com.galimagroup.gestionproduit.service.serviceInterface;

import com.galimagroup.gestionproduit.dto.ProduitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProduitInterface {

    // notre interface cest ici qu'on gere notre metier
    ProduitDto saveProduitDto(ProduitDto produitDto);

    Page<ProduitDto> findAllProduit(Pageable pageable);

    ProduitDto findProduitById(Long id);

    ProduitDto updateProduit(Long id, ProduitDto produitDto);
    void deleteProduit(Long id);
}
