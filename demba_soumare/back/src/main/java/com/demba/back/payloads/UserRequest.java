package com.demba.back.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    Long id,
    @NotNull(message = "Username is required!") @NotEmpty(message = "Username is required!") String username,
    @NotNull(message = "Firstname is required!") @NotEmpty(message = "Firstname is required!") String firstname,
    @NotNull(message = "Email is required!") @NotEmpty(message = "Email is required!") String email,
    @NotNull(message = "Password is required!") @NotEmpty(message = "Password is required!") String password
    ) {
}
