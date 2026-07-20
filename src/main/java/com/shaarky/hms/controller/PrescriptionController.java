package com.shaarky.hms.controller;

import com.shaarky.hms.common.ApiResponse;
import com.shaarky.hms.dto.request.PrescriptionRequest;
import com.shaarky.hms.dto.response.PrescriptionResponse;
import com.shaarky.hms.service.PrescriptionService;
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
@RequestMapping("/api/v1/prescriptions")
@RequiredArgsConstructor
@Tag(name = "Prescription Management", description = "REST APIs for managing prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @Operation(summary = "Create a new prescription")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> createPrescription(
            @Valid @RequestBody PrescriptionRequest prescriptionRequest) {

        PrescriptionResponse response =
                prescriptionService.createPrescription(prescriptionRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        HttpStatus.CREATED.value(),
                        "Prescription created successfully.",
                        response
                ));
    }

    @PutMapping("/{prescriptionId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @Operation(summary = "Update an existing prescription")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> updatePrescription(
            @PathVariable Long prescriptionId,
            @Valid @RequestBody PrescriptionRequest prescriptionRequest) {

        PrescriptionResponse response =
                prescriptionService.updatePrescription(prescriptionId, prescriptionRequest);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Prescription updated successfully.",
                        response
                )
        );
    }

    @GetMapping("/{prescriptionId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get prescription by ID")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> getPrescriptionById(
            @PathVariable Long prescriptionId) {

        PrescriptionResponse response =
                prescriptionService.getPrescriptionById(prescriptionId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Prescription retrieved successfully.",
                        response
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get all prescriptions")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getAllPrescriptions() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Prescriptions retrieved successfully.",
                        prescriptionService.getAllPrescriptions()
                )
        );
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get prescriptions by doctor")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getPrescriptionsByDoctor(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Doctor prescriptions retrieved successfully.",
                        prescriptionService.getPrescriptionsByDoctor(doctorId)
                )
        );
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get prescriptions by patient")
    public ResponseEntity<ApiResponse<List<PrescriptionResponse>>> getPrescriptionsByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Patient prescriptions retrieved successfully.",
                        prescriptionService.getPrescriptionsByPatient(patientId)
                )
        );
    }

    @GetMapping("/appointment/{appointmentId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get prescription by appointment")
    public ResponseEntity<ApiResponse<PrescriptionResponse>> getPrescriptionByAppointment(
            @PathVariable Long appointmentId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Prescription retrieved successfully.",
                        prescriptionService.getPrescriptionByAppointment(appointmentId)
                )
        );
    }

    @DeleteMapping("/{prescriptionId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a prescription")
    public ResponseEntity<ApiResponse<Void>> deletePrescription(
            @PathVariable Long prescriptionId) {

        prescriptionService.deletePrescription(prescriptionId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        "Prescription deleted successfully."
                )
        );
    }
}