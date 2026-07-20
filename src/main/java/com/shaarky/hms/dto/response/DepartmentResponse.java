package com.shaarky.hms.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DepartmentResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}