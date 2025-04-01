package sow.issa.recrutement.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sow.issa.recrutement.entities.CartEntity;
import sow.issa.recrutement.entities.UserEntity;
import sow.issa.recrutement.entities.WishlistEntity;
import sow.issa.recrutement.entities.enums.TokenType;
import sow.issa.recrutement.models.request.RegisterRequest;
import sow.issa.recrutement.models.request.SignInRequest;
import sow.issa.recrutement.models.response.SignInResponse;
import sow.issa.recrutement.repositories.UserRepository;
import sow.issa.recrutement.security.jwt.TokenProvider;
import sow.issa.recrutement.services.AuthenticationService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {
        var user = UserEntity.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .cart(CartEntity.builder().build())
                .wishlist(WishlistEntity.builder().build())
                .build();
        user.assignCartAndWishlist();
        userRepository.save(user);
    }

    @Override
    public SignInResponse singIn(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = tokenProvider.createToken(authentication, true);
        return SignInResponse
                .builder()
                .tokenType(TokenType.BEARER)
                .token(accessToken)
                .build();
    }
}
