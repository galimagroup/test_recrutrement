package com.demba.back.services;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demba.back.mappers.UserMapper;
import com.demba.back.models.User;
import com.demba.back.payloads.UserRequest;
import com.demba.back.payloads.UserResponse;
import com.demba.back.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository repository;
  
  @Autowired
  private UserMapper userMapper;

  public UserResponse createUser(UserRequest request) {
    User user = userMapper.toUser(request);
    user.setPassword(passwordEncoder.encode(request.password()));
    return userMapper.toUserResponse(repository.save(user));
  }
}
