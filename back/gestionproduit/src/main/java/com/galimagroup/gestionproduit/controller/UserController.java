package com.galimagroup.gestionproduit.controller;


import com.galimagroup.gestionproduit.dto.AuthReponseDto;
import com.galimagroup.gestionproduit.dto.LoginDto;
import com.galimagroup.gestionproduit.dto.RegisterDto;
import com.galimagroup.gestionproduit.service.serviceImp.UserServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
private  final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/account")
    public RegisterDto createAccount(@RequestBody RegisterDto registerDto) {
        return userService.saveUser(registerDto);
    }
    // Route pour se connecter
    @PostMapping("/token")
    public AuthReponseDto login(@RequestBody LoginDto loginDTO) {
        return userService.login(loginDTO);
    }
}
