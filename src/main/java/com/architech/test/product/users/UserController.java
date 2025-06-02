package com.architech.test.product.users;

import com.architech.test.product.utils.ApiErrorResponse;
import com.architech.test.product.utils.LoginRequest;
import com.architech.test.product.utils.LoginResponse;
import com.architech.test.product.utils.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user.", description = "Create a new  user.")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/account")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        log.info("REST Request to create a new User: {}", user);
        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(userService.addUser(user))
                .message("User created successfully.")
                .status(CREATED)
                .statusCode(CREATED.value())
                .build()
        );
    }

    @Operation(summary = "Authenticate user and return token", description = "Authenticate user and return token")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        log.info("REST Request to create a token : {}", loginRequest);
        return ResponseEntity.ok().body(
            Response.builder()
                .timeStamp(now())
                .data(userService.authenticate(loginRequest))
                .message("Token generated successfully.")
                .status(OK)
                .statusCode(OK.value())
                .build()
        );
    }
}
