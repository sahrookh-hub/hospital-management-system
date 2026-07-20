package com.shaarky.hms.repository;

import com.shaarky.hms.entity.RefreshToken;
import com.shaarky.hms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenAndDeletedFalse(String token);

    List<RefreshToken> findAllByUserAndRevokedFalseAndDeletedFalse(User user);

    void deleteAllByUser(User user);
}