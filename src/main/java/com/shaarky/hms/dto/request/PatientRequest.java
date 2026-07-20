package com.shaarky.hms.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequest {

    @NotBlank(message = "Patient ID is required.")
    @Size(max = 30, message = "Patient ID must not exceed 30 characters.")
    private String patientId;

    @NotBlank(message = "First name is required.")
    @Size(max = 100, message = "First name must not exceed 100 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 100, message = "Last name must not exceed 100 characters.")
    private String lastName;

    @Email(message = "Invalid email address.")
    @Size(max = 150, message = "Email must not exceed 150 characters.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be a valid 10-digit Indian mobile number."
    )
    private String phoneNumber;

    @NotNull(message = "Date of birth is required.")
    @Past(message = "Date of birth must be in the past.")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required.")
    @Pattern(
            regexp = "^(Male|Female|Other)$",
            message = "Gender must be Male, Female, or Other."
    )
    private String gender;

    @Size(max = 10, message = "Blood group must not exceed 10 characters.")
    private String bloodGroup;

    @NotBlank(message = "Address is required.")
    @Size(max = 500, message = "Address must not exceed 500 characters.")
    private String address;

    @NotBlank(message = "Emergency contact name is required.")
    @Size(max = 150, message = "Emergency contact name must not exceed 150 characters.")
    private String emergencyContactName;

    @NotBlank(message = "Emergency contact number is required.")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Emergency contact number must be a valid 10-digit Indian mobile number."
    )
    private String emergencyContactNumber;

    @Size(max = 1000, message = "Allergies must not exceed 1000 characters.")
    private String allergies;

    @Size(max = 2000, message = "Medical history must not exceed 2000 characters.")
    private String medicalHistory;
}