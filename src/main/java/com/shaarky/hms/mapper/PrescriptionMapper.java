package com.shaarky.hms.mapper;

import com.shaarky.hms.dto.request.PrescriptionRequest;
import com.shaarky.hms.dto.response.PrescriptionResponse;
import com.shaarky.hms.entity.Appointment;
import com.shaarky.hms.entity.Doctor;
import com.shaarky.hms.entity.Patient;
import com.shaarky.hms.entity.Prescription;
import org.springframework.stereotype.Component;

@Component
public class PrescriptionMapper {

    public Prescription toEntity(
            PrescriptionRequest request,
            Appointment appointment,
            Doctor doctor,
            Patient patient
    ) {

        if (request == null) {
            return null;
        }

        Prescription prescription = Prescription.builder()
                .prescriptionNumber(request.getPrescriptionNumber())
                .diagnosis(request.getDiagnosis())
                .medications(request.getMedications())
                .instructions(request.getInstructions())
                .followUpRequired(request.getFollowUpRequired())
                .followUpNotes(request.getFollowUpNotes())
                .build();

        prescription.assignAppointment(appointment);
        prescription.assignDoctor(doctor);
        prescription.assignPatient(patient);

        return prescription;
    }

    public PrescriptionResponse toResponse(Prescription prescription) {

        if (prescription == null) {
            return null;
        }

        return PrescriptionResponse.builder()
                .id(prescription.getId())
                .prescriptionNumber(prescription.getPrescriptionNumber())
                .appointmentId(prescription.getAppointment().getId())
                .doctorId(prescription.getDoctor().getId())
                .doctorName(prescription.getDoctor().getFullName())
                .patientId(prescription.getPatient().getId())
                .patientName(prescription.getPatient().getFullName())
                .diagnosis(prescription.getDiagnosis())
                .medications(prescription.getMedications())
                .instructions(prescription.getInstructions())
                .followUpRequired(prescription.getFollowUpRequired())
                .followUpNotes(prescription.getFollowUpNotes())
                .createdAt(prescription.getCreatedAt())
                .updatedAt(prescription.getUpdatedAt())
                .build();
    }

    public void updateEntity(
            Prescription prescription,
            PrescriptionRequest request,
            Appointment appointment,
            Doctor doctor,
            Patient patient
    ) {

        prescription.setPrescriptionNumber(request.getPrescriptionNumber());
        prescription.assignAppointment(appointment);
        prescription.assignDoctor(doctor);
        prescription.assignPatient(patient);
        prescription.setDiagnosis(request.getDiagnosis());
        prescription.setMedications(request.getMedications());
        prescription.setInstructions(request.getInstructions());
        prescription.setFollowUpRequired(request.getFollowUpRequired());
        prescription.setFollowUpNotes(request.getFollowUpNotes());
    }
}