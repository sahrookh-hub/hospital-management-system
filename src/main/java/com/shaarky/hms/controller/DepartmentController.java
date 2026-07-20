package com.shaarky.hms.controller;

import com.shaarky.hms.common.ApiResponse;
import com.shaarky.hms.constants.ApplicationConstants;
import com.shaarky.hms.dto.request.DepartmentRequest;
import com.shaarky.hms.dto.response.DepartmentResponse;
import com.shaarky.hms.entity.Department;
import com.shaarky.hms.mapper.DepartmentMapper;
import com.shaarky.hms.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApplicationConstants.API_BASE_PATH + "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> create(
            @Valid @RequestBody DepartmentRequest request
    ) {

        Department department = departmentService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                HttpStatus.CREATED.value(),
                                "Department created successfully.",
                                departmentMapper.toResponse(department)
                        )
                );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request
    ) {

        Department department = departmentService.update(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Department updated successfully.",
                        departmentMapper.toResponse(department)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<DepartmentResponse>> getById(
            @PathVariable Long id
    ) {

        Department department = departmentService.findById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Department fetched successfully.",
                        departmentMapper.toResponse(department)
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<ApiResponse<List<DepartmentResponse>>> getAll() {

        List<DepartmentResponse> departments = departmentService.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Departments fetched successfully.",
                        departments
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ) {

        departmentService.softDelete(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Department deleted successfully."
                )
        );
    }
}