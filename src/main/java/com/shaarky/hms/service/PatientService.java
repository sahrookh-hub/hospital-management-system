package com.shaarky.hms.service;

import com.shaarky.hms.dto.request.PatientRequest;
import com.shaarky.hms.dto.response.PatientResponse;

import java.util.List;

public interface PatientService {

    PatientResponse createPatient(PatientRequest patientRequest);

    PatientResponse updatePatient(Long patientId, PatientRequest patientRequest);

    PatientResponse getPatientById(Long patientId);

    List<PatientResponse> getAllPatients();

    void deletePatient(Long patientId);
}