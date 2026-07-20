package com.shaarky.hms.entity;

import com.shaarky.hms.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "doctors",
        indexes = {
                @Index(name = "idx_doctor_employee_id", columnList = "employee_id", unique = true),
                @Index(name = "idx_doctor_license_number", columnList = "license_number", unique = true),
                @Index(name = "idx_doctor_department", columnList = "department_id"),
                @Index(name = "idx_doctor_email", columnList = "email", unique = true),
                @Index(name = "idx_doctor_phone_number", columnList = "phone_number")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends BaseEntity {

    @Column(name = "employee_id", nullable = false, unique = true, length = 30)
    private String employeeId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "license_number", nullable = false, unique = true, length = 50)
    private String licenseNumber;

    @Column(name = "specialization", nullable = false, length = 120)
    private String specialization;

    @Column(name = "qualification", nullable = false, length = 200)
    private String qualification;

    @Column(name = "years_of_experience", nullable = false)
    private Integer yearsOfExperience;

    @Column(name = "consultation_fee", nullable = false)
    private Double consultationFee;

    @Column(name = "room_number", length = 20)
    private String roomNumber;

    @Column(name = "available", nullable = false)
    @Builder.Default
    private Boolean available = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "department_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_doctor_department")
    )
    @Setter(AccessLevel.NONE)
    private Department department;

    public void assignDepartment(Department department) {
        this.department = department;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}