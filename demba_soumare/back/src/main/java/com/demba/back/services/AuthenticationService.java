package com.demba.back.services;

import com.demba.back.enums.TokenType;
import com.demba.back.models.Token;
import com.demba.back.models.User;
import com.demba.back.payloads.AuthenticationRequest;
import com.demba.back.payloads.AuthenticationResponse;
import com.demba.back.repositories.TokenRepository;
import com.demba.back.repositories.UserRepository;
import com.demba.back.utils.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;


  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getEmail(),
        request.getPassword()
      )
    );
    var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse
      .builder()
      .token(jwtToken)
      .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token
      .builder()
      .user(user)
      .token(jwtToken)
      .tokenType(TokenType.BEARER)
      .expired(false)
      .revoked(false)
      .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty()) return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

}
