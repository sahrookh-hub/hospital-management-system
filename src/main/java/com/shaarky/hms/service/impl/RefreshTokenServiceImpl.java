package com.shaarky.hms.service.impl;

import com.shaarky.hms.entity.RefreshToken;
import com.shaarky.hms.entity.User;
import com.shaarky.hms.exception.BadRequestException;
import com.shaarky.hms.repository.RefreshTokenRepository;
import com.shaarky.hms.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository refreshTokenRepository
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken create(User user) {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(
                LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000)
        );
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken validate(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByTokenAndDeletedFalse(token)
                .orElseThrow(() ->
                        new BadRequestException("Invalid refresh token."));

        if (!refreshToken.isValid()) {
            throw new BadRequestException("Refresh token has expired or has been revoked.");
        }

        return refreshToken;
    }

    @Override
    public void revoke(String token) {

        refreshTokenRepository.findByTokenAndDeletedFalse(token)
                .ifPresent(refreshToken -> {
                    refreshToken.setRevoked(true);
                    refreshTokenRepository.save(refreshToken);
                });
    }

    @Override
    public void revokeAll(User user) {

        List<RefreshToken> refreshTokens =
                refreshTokenRepository.findAllByUserAndRevokedFalseAndDeletedFalse(user);

        refreshTokens.forEach(token -> token.setRevoked(true));

        refreshTokenRepository.saveAll(refreshTokens);
    }
}