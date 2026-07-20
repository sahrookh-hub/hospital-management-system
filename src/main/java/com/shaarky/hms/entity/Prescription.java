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
        name = "prescriptions",
        indexes = {
                @Index(name = "idx_prescription_number", columnList = "prescription_number", unique = true),
                @Index(name = "idx_prescription_appointment", columnList = "appointment_id", unique = true),
                @Index(name = "idx_prescription_doctor", columnList = "doctor_id"),
                @Index(name = "idx_prescription_patient", columnList = "patient_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prescription extends BaseEntity {

    @Column(name = "prescription_number", nullable = false, unique = true, length = 30)
    private String prescriptionNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "appointment_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_prescription_appointment")
    )
    @Setter(AccessLevel.NONE)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_prescription_doctor")
    )
    @Setter(AccessLevel.NONE)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "patient_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_prescription_patient")
    )
    @Setter(AccessLevel.NONE)
    private Patient patient;

    @Column(name = "diagnosis", nullable = false, length = 1000)
    private String diagnosis;

    @Column(name = "medications", nullable = false, length = 3000)
    private String medications;

    @Column(name = "instructions", nullable = false, length = 2000)
    private String instructions;

    @Column(name = "follow_up_required", nullable = false)
    @Builder.Default
    private Boolean followUpRequired = false;

    @Column(name = "follow_up_notes", length = 1000)
    private String followUpNotes;

    public void assignAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void assignDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void assignPatient(Patient patient) {
        this.patient = patient;
    }
}