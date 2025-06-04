package com.backend.procuct_backend.service;

import com.backend.procuct_backend.dao.IUserRepository;
import com.backend.procuct_backend.dto.User;
import com.backend.procuct_backend.exception.EntityNotFoundException;
import com.backend.procuct_backend.exception.RequestException;
import com.backend.procuct_backend.mapping.UserMapper;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class UserService {
    private final IUserRepository iUserRepository;
    private final UserMapper userMapper;
    private final MessageSource messageSource;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository iUserRepository, UserMapper userMapper,
                       MessageSource messageSource, PasswordEncoder passwordEncoder) {
        this.iUserRepository = iUserRepository;
        this.userMapper = userMapper;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<User> getUser() {
        return iUserRepository.findAll()
                .stream()
                .map(userMapper::toUser)
                .toList();
    }

    @Transactional(readOnly = true)
    public User getUserId(int id) {
        return userMapper.toUser(iUserRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("user.notfound", new Object[]{id},
                                Locale.getDefault()))));
    }

    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toUser(iUserRepository.save(userMapper.fromUser(user)));
    }

    @Transactional
    public User updateUser(int id, User user) {
        return iUserRepository.findById(id)
                .map(entity -> {
                    user.setId(id);
                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(user.getPassword()));
                    }
                    return userMapper.toUser(
                            iUserRepository.save(userMapper.fromUser(user)));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("user.notfound", new Object[]{id},
                        Locale.getDefault())));
    }

    @Transactional
    public void deleteUser(int id){
        try {
            iUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("user.error-deletion", new Object[]{id},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}