package sow.issa.recrutement.security.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import sow.issa.recrutement.repositories.UserRepository;

import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DomainUserDetailsService implements UserDetailsService {
    final UserRepository userRepository;

    @Override
    public User loadUserByUsername(final String email) {

        log.debug("Authenticating {}", email);

         var userEntity = userRepository.findByEmail(email)
                 .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User {0}  was not found in the database", email)));

        return new User(userEntity.getEmail(), userEntity.getPassword(), new ArrayList<>());
    }
}
