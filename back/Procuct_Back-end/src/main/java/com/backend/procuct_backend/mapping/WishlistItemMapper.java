package com.backend.procuct_backend.mapping;

import com.backend.procuct_backend.dto.WishlistItem;
import com.backend.procuct_backend.entitie.WishlistItemEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WishlistItemMapper {
    WishlistItem toWishlistItem(WishlistItemEntity wishlistItemEntity);
    WishlistItemEntity fromWishlistItem(WishlistItem wishlistItem);
}
