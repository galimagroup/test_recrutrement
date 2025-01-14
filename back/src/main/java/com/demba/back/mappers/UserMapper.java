package com.demba.back.mappers;

import org.springframework.stereotype.Service;

import com.demba.back.models.User;
import com.demba.back.payloads.UserRequest;
import com.demba.back.payloads.UserResponse;

@Service
public class UserMapper {

    public User toUser(UserRequest request) {
        return User.builder()
            .id(request.id())
            .username(request.username())
            .firstname(request.firstname())
            .email(request.email())
            .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .username(user.getUsername())
            .firstname(user.getFirstname())
            .email(user.getEmail())
            .build();
    }
}