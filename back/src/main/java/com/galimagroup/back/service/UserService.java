package com.galimagroup.back.service;

import com.galimagroup.back.entities.User;

public interface UserService {
    User createUser(User user);
    User findByEmail(String email);
}
