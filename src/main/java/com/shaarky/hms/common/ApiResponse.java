package com.shaarky.hms.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        int status,
        String message,
        T data,
        LocalDateTime timestamp
) {

    /* =========================
       Success Responses
       ========================= */

    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(status)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(int status, String message) {
        return success(status, message, null);
    }

    /**
     * Uses HTTP 200 OK by default.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return success(HttpStatus.OK.value(), message, data);
    }

    /**
     * Uses HTTP 200 OK by default.
     */
    public static <T> ApiResponse<T> success(String message) {
        return success(HttpStatus.OK.value(), message, null);
    }

    /* =========================
       Error Responses
       ========================= */

    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String message, T data) {
        return ApiResponse.<T>builder()
                .success(false)
                .status(status)
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
}