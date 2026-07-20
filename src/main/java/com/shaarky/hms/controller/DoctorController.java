package com.shaarky.hms.controller;

import com.shaarky.hms.common.ApiResponse;
import com.shaarky.hms.dto.request.DoctorRequest;
import com.shaarky.hms.dto.response.DoctorResponse;
import com.shaarky.hms.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor Management", description = "REST APIs for managing doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> createDoctor(
            @Valid @RequestBody DoctorRequest doctorRequest) {

        DoctorResponse doctorResponse = doctorService.createDoctor(doctorRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Doctor created successfully.",
                        doctorResponse
                ));
    }

    @PutMapping("/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update an existing doctor")
    public ResponseEntity<ApiResponse<DoctorResponse>> updateDoctor(
            @PathVariable Long doctorId,
            @Valid @RequestBody DoctorRequest doctorRequest) {

        DoctorResponse doctorResponse = doctorService.updateDoctor(doctorId, doctorRequest);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor updated successfully.",
                        doctorResponse
                )
        );
    }

    @GetMapping("/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get doctor by ID")
    public ResponseEntity<ApiResponse<DoctorResponse>> getDoctorById(
            @PathVariable Long doctorId) {

        DoctorResponse doctorResponse = doctorService.getDoctorById(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor retrieved successfully.",
                        doctorResponse
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get all doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAllDoctors() {

        List<DoctorResponse> doctorResponses = doctorService.getAllDoctors();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctors retrieved successfully.",
                        doctorResponses
                )
        );
    }

    @GetMapping("/department/{departmentId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get doctors by department")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getDoctorsByDepartment(
            @PathVariable Long departmentId) {

        List<DoctorResponse> doctorResponses =
                doctorService.getDoctorsByDepartment(departmentId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Department doctors retrieved successfully.",
                        doctorResponses
                )
        );
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get all available doctors")
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> getAvailableDoctors() {

        List<DoctorResponse> doctorResponses =
                doctorService.getAvailableDoctors();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Available doctors retrieved successfully.",
                        doctorResponses
                )
        );
    }

    @DeleteMapping("/{doctorId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a doctor")
    public ResponseEntity<ApiResponse<Void>> deleteDoctor(
            @PathVariable Long doctorId) {

        doctorService.deleteDoctor(doctorId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor deleted successfully.",
                        null
                )
        );
    }
}