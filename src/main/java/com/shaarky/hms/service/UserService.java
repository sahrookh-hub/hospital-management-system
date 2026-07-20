package com.shaarky.hms.service;

import com.shaarky.hms.dto.request.RegisterUserRequest;
import com.shaarky.hms.entity.User;

public interface UserService {

    User register(RegisterUserRequest request);

    User findByEmail(String email);

    User findById(Long id);

    void softDelete(Long id);
}