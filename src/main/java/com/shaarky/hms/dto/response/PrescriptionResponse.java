package com.shaarky.hms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {

    private Long id;

    private String prescriptionNumber;

    private Long appointmentId;

    private String appointmentNumber;

    private Long doctorId;

    private String doctorName;

    private Long patientId;

    private String patientName;

    private String diagnosis;

    private String medications;

    private String instructions;

    private Boolean followUpRequired;

    private String followUpNotes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}