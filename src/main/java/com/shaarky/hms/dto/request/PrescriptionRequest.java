package com.shaarky.hms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionRequest {

    @NotBlank(message = "Prescription number is required.")
    @Size(max = 30, message = "Prescription number must not exceed 30 characters.")
    private String prescriptionNumber;

    @NotNull(message = "Appointment ID is required.")
    private Long appointmentId;

    @NotNull(message = "Doctor ID is required.")
    private Long doctorId;

    @NotNull(message = "Patient ID is required.")
    private Long patientId;

    @NotBlank(message = "Diagnosis is required.")
    @Size(max = 1000, message = "Diagnosis must not exceed 1000 characters.")
    private String diagnosis;

    @NotBlank(message = "Medications are required.")
    @Size(max = 3000, message = "Medications must not exceed 3000 characters.")
    private String medications;

    @NotBlank(message = "Instructions are required.")
    @Size(max = 2000, message = "Instructions must not exceed 2000 characters.")
    private String instructions;

    @NotNull(message = "Follow-up requirement is required.")
    private Boolean followUpRequired;

    @Size(max = 1000, message = "Follow-up notes must not exceed 1000 characters.")
    private String followUpNotes;
}