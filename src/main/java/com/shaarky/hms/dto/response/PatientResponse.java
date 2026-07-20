package com.shaarky.hms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse {

    private Long id;

    private String patientId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private String bloodGroup;

    private String address;

    private String emergencyContactName;

    private String emergencyContactNumber;

    private String allergies;

    private String medicalHistory;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}