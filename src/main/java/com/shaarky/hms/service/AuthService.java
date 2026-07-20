package com.shaarky.hms.service;

import com.shaarky.hms.dto.request.LoginRequest;
import com.shaarky.hms.dto.request.RegisterUserRequest;
import com.shaarky.hms.dto.response.AuthResponse;

public interface AuthService {

    /**
     * Register a new user.
     *
     * @param request registration request
     * @return authentication response containing JWT and refresh token
     */
    AuthResponse register(RegisterUserRequest request);

    /**
     * Authenticate an existing user.
     *
     * @param request login request
     * @return authentication response containing JWT and refresh token
     */
    AuthResponse login(LoginRequest request);

    /**
     * Generate a new access token using a refresh token.
     *
     * @param refreshToken refresh token
     * @return new authentication response
     */
    AuthResponse refreshToken(String refreshToken);
}