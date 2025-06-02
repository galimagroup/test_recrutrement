package com.architech.test.product.users;

import com.architech.test.product.utils.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User addUser(User users);
    ResponseEntity<?> authenticate(LoginRequest loginRequest);
}
