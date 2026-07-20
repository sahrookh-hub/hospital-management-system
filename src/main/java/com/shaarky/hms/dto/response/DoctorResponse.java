package com.shaarky.hms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {

    private Long id;

    private String employeeId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private String phoneNumber;

    private String licenseNumber;

    private String specialization;

    private String qualification;

    private Integer yearsOfExperience;

    private BigDecimal consultationFee;

    private String roomNumber;

    private Boolean available;

    private Long departmentId;

    private String departmentName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}