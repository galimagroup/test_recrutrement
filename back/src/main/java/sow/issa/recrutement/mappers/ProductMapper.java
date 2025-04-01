package sow.issa.recrutement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sow.issa.recrutement.entities.ProductEntity;
import sow.issa.recrutement.models.request.ProductRequest;
import sow.issa.recrutement.models.response.ProductResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductRequest, ProductResponse, ProductEntity> {
}
