package com.shaarky.hms.controller;

import com.shaarky.hms.common.ApiResponse;
import com.shaarky.hms.constants.ApplicationConstants;
import com.shaarky.hms.dto.request.RegisterUserRequest;
import com.shaarky.hms.dto.response.UserResponse;
import com.shaarky.hms.entity.User;
import com.shaarky.hms.mapper.UserMapper;
import com.shaarky.hms.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.API_BASE_PATH + "/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> register(
            @Valid @RequestBody RegisterUserRequest request
    ) {

        User user = userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED.value(),
                                "User registered successfully.",
                                userMapper.toResponse(user)
                        )
                );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getById(
            @PathVariable Long id
    ) {

        User user = userService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "User fetched successfully.",
                        userMapper.toResponse(user)
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ) {

        userService.softDelete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "User deleted successfully."
                )
        );
    }
}