package com.backend.procuct_backend.controller;

import com.backend.procuct_backend.dto.User;
import com.backend.procuct_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    @GetMapping("/hello")
    public List<User> getUser() {
        return userService.getUser();
    }
    @GetMapping("/{id}")
    public User getUserId(@PathVariable("id") int id) {
        return userService.getUserId(id);
    }
    @PostMapping("/account")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteAppUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
    }
}


