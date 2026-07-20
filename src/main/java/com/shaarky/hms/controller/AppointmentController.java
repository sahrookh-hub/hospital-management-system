package com.shaarky.hms.controller;

import com.shaarky.hms.common.ApiResponse;
import com.shaarky.hms.dto.request.AppointmentRequest;
import com.shaarky.hms.dto.response.AppointmentResponse;
import com.shaarky.hms.service.AppointmentService;
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
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
@Tag(name = "Appointment Management", description = "REST APIs for managing appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @Operation(summary = "Create a new appointment")
    public ResponseEntity<ApiResponse<AppointmentResponse>> createAppointment(
            @Valid @RequestBody AppointmentRequest appointmentRequest) {

        AppointmentResponse response =
                appointmentService.createAppointment(appointmentRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Appointment created successfully.",
                        response
                ));
    }

    @PutMapping("/{appointmentId}")
    @PreAuthorize("hasAnyRole('ADMIN','RECEPTIONIST')")
    @Operation(summary = "Update an appointment")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(
            @PathVariable Long appointmentId,
            @Valid @RequestBody AppointmentRequest appointmentRequest) {

        AppointmentResponse response =
                appointmentService.updateAppointment(appointmentId, appointmentRequest);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment updated successfully.",
                        response
                )
        );
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get appointment by ID")
    public ResponseEntity<ApiResponse<AppointmentResponse>> getAppointmentById(
            @PathVariable Long appointmentId) {

        AppointmentResponse response =
                appointmentService.getAppointmentById(appointmentId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment retrieved successfully.",
                        response
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get all appointments")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAllAppointments() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments retrieved successfully.",
                        appointmentService.getAllAppointments()
                )
        );
    }

    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get appointments by doctor")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByDoctor(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Doctor appointments retrieved successfully.",
                        appointmentService.getAppointmentsByDoctor(doctorId)
                )
        );
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get appointments by patient")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Patient appointments retrieved successfully.",
                        appointmentService.getAppointmentsByPatient(patientId)
                )
        );
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTIONIST')")
    @Operation(summary = "Get appointments by status")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointments retrieved successfully.",
                        appointmentService.getAppointmentsByStatus(status)
                )
        );
    }

    @DeleteMapping("/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete an appointment")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(
            @PathVariable Long appointmentId) {

        appointmentService.deleteAppointment(appointmentId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Appointment deleted successfully.",
                        null
                )
        );
    }
}