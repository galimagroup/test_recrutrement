package com.galimagroup.back.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String firstname;
    private String email;
    private String password;
}