package com.shaarky.hms.entity;

import com.shaarky.hms.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
        name = "patients",
        indexes = {
                @Index(name = "idx_patient_patient_id", columnList = "patient_id", unique = true),
                @Index(name = "idx_patient_email", columnList = "email", unique = true),
                @Index(name = "idx_patient_phone_number", columnList = "phone_number"),
                @Index(name = "idx_patient_blood_group", columnList = "blood_group")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends BaseEntity {

    @Column(name = "patient_id", nullable = false, unique = true, length = 30)
    private String patientId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", unique = true, length = 150)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "gender", nullable = false, length = 20)
    private String gender;

    @Column(name = "blood_group", length = 10)
    private String bloodGroup;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    @Column(name = "emergency_contact_name", nullable = false, length = 150)
    private String emergencyContactName;

    @Column(name = "emergency_contact_number", nullable = false, length = 20)
    private String emergencyContactNumber;

    @Column(name = "allergies", length = 1000)
    private String allergies;

    @Column(name = "medical_history", length = 2000)
    private String medicalHistory;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}