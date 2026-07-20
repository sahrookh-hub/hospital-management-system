package com.shaarky.hms.service;

import com.shaarky.hms.entity.RefreshToken;
import com.shaarky.hms.entity.User;

public interface RefreshTokenService {

    RefreshToken create(User user);

    RefreshToken validate(String token);

    void revoke(String token);

    void revokeAll(User user);
}