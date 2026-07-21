package com.shaarky.hms.mapper;

import com.shaarky.hms.dto.request.DoctorRequest;
import com.shaarky.hms.dto.response.DoctorResponse;
import com.shaarky.hms.entity.Department;
import com.shaarky.hms.entity.Doctor;
import com.shaarky.hms.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    public Doctor toEntity(
            DoctorRequest request,
            Department department,
            User user
    ) {
        if (request == null) {
            return null;
        }

        Doctor doctor = Doctor.builder()
                .employeeId(request.getEmployeeId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .licenseNumber(request.getLicenseNumber())
                .specialization(request.getSpecialization())
                .qualification(request.getQualification())
                .yearsOfExperience(request.getYearsOfExperience())
                .consultationFee(request.getConsultationFee())
                .roomNumber(request.getRoomNumber())
                .available(request.getAvailable())
                .build();

        doctor.assignDepartment(department);
        doctor.assignUser(user);

        return doctor;
    }

    public DoctorResponse toResponse(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        return DoctorResponse.builder()
                .id(doctor.getId())
                .employeeId(doctor.getEmployeeId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .fullName(doctor.getFullName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .licenseNumber(doctor.getLicenseNumber())
                .specialization(doctor.getSpecialization())
                .qualification(doctor.getQualification())
                .yearsOfExperience(doctor.getYearsOfExperience())
                .consultationFee(
                        doctor.getConsultationFee() == null
                                ? null
                                : java.math.BigDecimal.valueOf(doctor.getConsultationFee())
                )
                .roomNumber(doctor.getRoomNumber())
                .available(doctor.getAvailable())
                .departmentId(
                        doctor.getDepartment() != null
                                ? doctor.getDepartment().getId()
                                : null
                )
                .departmentName(
                        doctor.getDepartment() != null
                                ? doctor.getDepartment().getName()
                                : null
                )
                .userId(
                        doctor.getUser() != null
                                ? doctor.getUser().getId()
                                : null
                )
                .userEmail(
                        doctor.getUser() != null
                                ? doctor.getUser().getEmail()
                                : null
                )
                .createdAt(doctor.getCreatedAt())
                .updatedAt(doctor.getUpdatedAt())
                .build();
    }

    public void updateEntity(
            Doctor doctor,
            DoctorRequest request,
            Department department,
            User user
    ) {
        doctor.setEmployeeId(request.getEmployeeId());
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setRoomNumber(request.getRoomNumber());
        doctor.setAvailable(request.getAvailable());

        doctor.assignDepartment(department);
        doctor.assignUser(user);
    }
}