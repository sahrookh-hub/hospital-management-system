package com.shaarky.hms.service;

import com.shaarky.hms.dto.request.PrescriptionRequest;
import com.shaarky.hms.dto.response.PrescriptionResponse;

import java.util.List;

public interface PrescriptionService {

    PrescriptionResponse createPrescription(PrescriptionRequest prescriptionRequest);

    PrescriptionResponse updatePrescription(Long prescriptionId, PrescriptionRequest prescriptionRequest);

    PrescriptionResponse getPrescriptionById(Long prescriptionId);

    List<PrescriptionResponse> getAllPrescriptions();

    List<PrescriptionResponse> getPrescriptionsByDoctor(Long doctorId);

    List<PrescriptionResponse> getPrescriptionsByPatient(Long patientId);

    PrescriptionResponse getPrescriptionByAppointment(Long appointmentId);

    void deletePrescription(Long prescriptionId);
}