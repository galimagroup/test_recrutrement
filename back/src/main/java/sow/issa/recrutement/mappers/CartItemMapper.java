package sow.issa.recrutement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import sow.issa.recrutement.entities.CartItemEntity;
import sow.issa.recrutement.entities.ProductEntity;
import sow.issa.recrutement.models.request.CartItemRequest;
import sow.issa.recrutement.models.request.ProductRequest;
import sow.issa.recrutement.models.response.CartItemResponse;
import sow.issa.recrutement.models.response.ProductResponse;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CartItemMapper extends EntityMapper<CartItemRequest, CartItemResponse, CartItemEntity> {
    @Override
    @Mapping(target = "product.id", source = "productId")
    CartItemEntity asEntity(CartItemRequest request);
}
