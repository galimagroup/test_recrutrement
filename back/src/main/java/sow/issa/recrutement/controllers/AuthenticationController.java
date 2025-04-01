package sow.issa.recrutement.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sow.issa.recrutement.models.request.RegisterRequest;
import sow.issa.recrutement.models.request.SignInRequest;
import sow.issa.recrutement.models.response.SignInResponse;
import sow.issa.recrutement.services.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

//    @Operation(summary = "sign in by login and password", description = "dhdg")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Success"),
//            @ApiResponse(responseCode = "400", description = "Request sent by the phase was syntactically incorrect"),
//            @ApiResponse(responseCode = "500", description = "Internal server error during request processing")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/account")
    public void register(@RequestBody @Valid RegisterRequest registerRequest) {
        authenticationService.register(registerRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/token")
    public SignInResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authenticationService.singIn(signInRequest);
    }
}
