package com.shaarky.hms.controller;

import com.shaarky.hms.common.ApiResponse;
import com.shaarky.hms.dto.request.PatientRequest;
import com.shaarky.hms.dto.response.PatientResponse;
import com.shaarky.hms.service.PatientService;
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
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Patient Management", description = "REST APIs for managing patients")
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @Operation(summary = "Create a new patient")
    public ResponseEntity<ApiResponse<PatientResponse>> createPatient(
            @Valid @RequestBody PatientRequest patientRequest) {

        PatientResponse patientResponse = patientService.createPatient(patientRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Patient created successfully.",
                        patientResponse
                ));
    }

    @PutMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @Operation(summary = "Update an existing patient")
    public ResponseEntity<ApiResponse<PatientResponse>> updatePatient(
            @PathVariable Long patientId,
            @Valid @RequestBody PatientRequest patientRequest) {

        PatientResponse patientResponse =
                patientService.updatePatient(patientId, patientRequest);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient updated successfully.",
                        patientResponse
                )
        );
    }

    @GetMapping("/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get patient by ID")
    public ResponseEntity<ApiResponse<PatientResponse>> getPatientById(
            @PathVariable Long patientId) {

        PatientResponse patientResponse =
                patientService.getPatientById(patientId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient retrieved successfully.",
                        patientResponse
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get all patients")
    public ResponseEntity<ApiResponse<List<PatientResponse>>> getAllPatients() {

        List<PatientResponse> patientResponses =
                patientService.getAllPatients();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patients retrieved successfully.",
                        patientResponses
                )
        );
    }

    @DeleteMapping("/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a patient")
    public ResponseEntity<ApiResponse<Void>> deletePatient(
            @PathVariable Long patientId) {

        patientService.deletePatient(patientId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient deleted successfully.",
                        null
                )
        );
    }
}