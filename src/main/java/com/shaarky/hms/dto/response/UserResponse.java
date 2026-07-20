package com.shaarky.hms.dto.response;

import com.shaarky.hms.enums.Role;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        boolean enabled,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}