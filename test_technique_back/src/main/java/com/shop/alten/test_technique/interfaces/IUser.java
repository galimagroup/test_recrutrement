package com.shop.alten.test_technique.interfaces;

import com.shop.alten.test_technique.model.User;

public interface IUser {
    User registerUser(User user);
    User findByEmail(String email);
    User findByUsername(String username);
}
