package com.shaarky.hms.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class DoctorRequest {

    @NotBlank(message = "Employee ID is required.")
    @Size(max = 30, message = "Employee ID must not exceed 30 characters.")
    private String employeeId;

    @NotBlank(message = "First name is required.")
    @Size(max = 100, message = "First name must not exceed 100 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 100, message = "Last name must not exceed 100 characters.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Please provide a valid email address.")
    @Size(max = 150, message = "Email must not exceed 150 characters.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Phone number must be a valid 10-digit Indian mobile number."
    )
    private String phoneNumber;

    @NotBlank(message = "Medical license number is required.")
    @Size(max = 50, message = "License number must not exceed 50 characters.")
    private String licenseNumber;

    @NotBlank(message = "Specialization is required.")
    @Size(max = 120, message = "Specialization must not exceed 120 characters.")
    private String specialization;

    @NotBlank(message = "Qualification is required.")
    @Size(max = 200, message = "Qualification must not exceed 200 characters.")
    private String qualification;

    @NotNull(message = "Years of experience is required.")
    @Min(value = 0, message = "Years of experience cannot be negative.")
    @Max(value = 60, message = "Years of experience cannot exceed 60.")
    private Integer yearsOfExperience;

    @NotNull(message = "Consultation fee is required.")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Consultation fee must be greater than zero."
    )
    private Double consultationFee;

    @Size(max = 20, message = "Room number must not exceed 20 characters.")
    private String roomNumber;

    @NotNull(message = "Availability status is required.")
    private Boolean available;

    @NotNull(message = "Department ID is required.")
    private Long departmentId;

    @NotNull(message = "User ID is required.")
    private Long userId;
}