package start.amdev.test.Mapper.Produit;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import start.amdev.test.Mapper.EntityMapper;
import start.amdev.test.domain.Model.Panier;
import start.amdev.test.domain.Model.Produit;
import start.amdev.test.web.dtos.requests.PanierRequestDTO;
import start.amdev.test.web.dtos.responses.PanierResponseDTO;
import start.amdev.test.web.dtos.responses.ProduitResponseDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper//(componentModel = "spring")
public interface PanierMapper extends EntityMapper<Panier, PanierRequestDTO, PanierResponseDTO> {
    @Named("ignoreTraining")
    PanierResponseDTO toDtoWithout(Panier panier);

    default List<PanierResponseDTO> toDtoList(List<Panier> entityList) {
        return entityList.stream()
                .map(this::toDtoWithout)
                .collect(Collectors.toList());
    }

    default Page<PanierResponseDTO> toDtoPage(Page<Panier> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<PanierResponseDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }

    // Mapping pour ajouter un produit au panier
    @InheritConfiguration
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "total", ignore = true)
    void updateEntityFromDto(PanierRequestDTO dto, @MappingTarget Panier entity);
}
