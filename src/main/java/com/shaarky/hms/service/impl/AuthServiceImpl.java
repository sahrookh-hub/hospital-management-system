package com.shaarky.hms.service.impl;

import com.shaarky.hms.dto.request.LoginRequest;
import com.shaarky.hms.dto.request.RegisterUserRequest;
import com.shaarky.hms.dto.response.AuthResponse;
import com.shaarky.hms.entity.RefreshToken;
import com.shaarky.hms.entity.User;
import com.shaarky.hms.exception.BadRequestException;
import com.shaarky.hms.exception.ResourceNotFoundException;
import com.shaarky.hms.repository.UserRepository;
import com.shaarky.hms.security.CustomUserDetails;
import com.shaarky.hms.service.AuthService;
import com.shaarky.hms.service.RefreshTokenService;
import com.shaarky.hms.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse register(RegisterUserRequest request) {

        if (userRepository.existsByEmailAndDeletedFalse(request.getEmail())) {
            throw new BadRequestException(
                    "User already exists with email : " + request.getEmail()
            );
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());

        user.changePassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setEnabled(true);

        user = userRepository.save(user);

        RefreshToken refreshToken =
                refreshTokenService.create(user);

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        String accessToken =
                jwtUtil.generateAccessToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getAccessTokenExpiration())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(

                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )

        );

        User user = userRepository
                .findByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "email",
                                request.getEmail()
                        ));

        refreshTokenService.revokeAll(user);

        RefreshToken refreshToken =
                refreshTokenService.create(user);

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        String accessToken =
                jwtUtil.generateAccessToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getAccessTokenExpiration())
                .build();
    }
    @Override
    public AuthResponse refreshToken(String refreshToken) {

        RefreshToken storedToken =
                refreshTokenService.validate(refreshToken);

        User user = storedToken.getUser();

        CustomUserDetails userDetails =
                new CustomUserDetails(user);

        String accessToken =
                jwtUtil.generateAccessToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(storedToken.getToken())
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getAccessTokenExpiration())
                .build();
    }

}