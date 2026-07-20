package com.shaarky.hms.dto.request;

import com.shaarky.hms.enums.AppointmentStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {

    @NotNull(message = "Doctor ID is required.")
    private Long doctorId;

    @NotNull(message = "Patient ID is required.")
    private Long patientId;

    @NotNull(message = "Appointment date is required.")
    @FutureOrPresent(message = "Appointment date must be today or in the future.")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required.")
    private LocalTime appointmentTime;

    @NotBlank(message = "Reason is required.")
    @Size(max = 500, message = "Reason must not exceed 500 characters.")
    private String reason;

    @NotNull(message = "Appointment status is required.")
    private AppointmentStatus status;
}