package start.amdev.test.Mapper.Produit;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import start.amdev.test.Mapper.EntityMapper;
import start.amdev.test.domain.Model.Produit;
import start.amdev.test.web.dtos.responses.ProduitResponseDTO;
import start.amdev.test.web.dtos.requests.ProduitRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
        //unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProduitMapper extends EntityMapper<Produit, ProduitRequestDTO, ProduitResponseDTO> {

    @Named("ignoreTraining")
    ProduitResponseDTO toDtoWithout(Produit produit);

    default List<ProduitResponseDTO> toDtoList(List<Produit> entityList) {

        return entityList.stream().map(this::toDtoWithout).collect(Collectors.toList());
    }

    default Page<ProduitResponseDTO> toDtoPage(Page<Produit> entityPage) {
        Pageable pageable = entityPage.getPageable();
        List<ProduitResponseDTO> dtoList = toDtoList(entityPage.getContent());
        return new PageImpl<>(dtoList, pageable, entityPage.getTotalElements());
    }
}
