package com.backend.procuct_backend.mapping;

import com.backend.procuct_backend.dto.User;
import com.backend.procuct_backend.entitie.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toUser(UserEntity appUserEntity);
    UserEntity fromUser(User user);
}
