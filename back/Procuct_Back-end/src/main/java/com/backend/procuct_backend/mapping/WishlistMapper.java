package com.backend.procuct_backend.mapping;

import com.backend.procuct_backend.dto.Wishlist;
import com.backend.procuct_backend.entitie.WishlistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WishlistMapper {
    Wishlist toWishlist(WishlistEntity wishlistEntity);

    WishlistEntity fromWishlist(Wishlist wishlist);
}
