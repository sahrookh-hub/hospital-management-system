package com.shaarky.hms.service.impl;

import com.shaarky.hms.dto.request.RegisterUserRequest;
import com.shaarky.hms.entity.User;
import com.shaarky.hms.exception.DuplicateResourceException;
import com.shaarky.hms.exception.ResourceNotFoundException;
import com.shaarky.hms.repository.UserRepository;
import com.shaarky.hms.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(RegisterUserRequest request) {

        if (userRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new DuplicateResourceException(
                    "User",
                    "email",
                    request.getEmail()
            );
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setEnabled(true);
        user.changePassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {

        return userRepository.findByEmailAndDeletedFalse(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                email
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {

        return userRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                id
                        )
                );
    }

    @Override
    public void softDelete(Long id) {

        User user = findById(id);
        user.setDeleted(true);

        userRepository.save(user);
    }
}