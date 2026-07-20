package com.shaarky.hms.service.impl;

import com.shaarky.hms.dto.request.PatientRequest;
import com.shaarky.hms.dto.response.PatientResponse;
import com.shaarky.hms.entity.Patient;
import com.shaarky.hms.exception.DuplicateResourceException;
import com.shaarky.hms.exception.ResourceNotFoundException;
import com.shaarky.hms.mapper.PatientMapper;
import com.shaarky.hms.repository.PatientRepository;
import com.shaarky.hms.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    public PatientResponse createPatient(PatientRequest patientRequest) {

        if (patientRepository.existsByPatientId(patientRequest.getPatientId())) {
            throw new DuplicateResourceException("Patient with patient ID already exists.");
        }

        if (patientRequest.getEmail() != null
                && !patientRequest.getEmail().isBlank()
                && patientRepository.existsByEmail(patientRequest.getEmail())) {
            throw new DuplicateResourceException("Patient with email already exists.");
        }

        if (patientRepository.existsByPhoneNumber(patientRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("Patient with phone number already exists.");
        }

        Patient patient = patientMapper.toEntity(patientRequest);

        Patient savedPatient = patientRepository.save(patient);

        log.info("Patient created successfully with ID: {}", savedPatient.getId());

        return patientMapper.toResponse(savedPatient);
    }

    @Override
    public PatientResponse updatePatient(Long patientId, PatientRequest patientRequest) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId));

        if (!patient.getPatientId().equals(patientRequest.getPatientId())
                && patientRepository.existsByPatientId(patientRequest.getPatientId())) {
            throw new DuplicateResourceException("Patient with patient ID already exists.");
        }

        if (patientRequest.getEmail() != null
                && !patientRequest.getEmail().isBlank()
                && !patientRequest.getEmail().equalsIgnoreCase(patient.getEmail())
                && patientRepository.existsByEmail(patientRequest.getEmail())) {
            throw new DuplicateResourceException("Patient with email already exists.");
        }

        if (!patient.getPhoneNumber().equals(patientRequest.getPhoneNumber())
                && patientRepository.existsByPhoneNumber(patientRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("Patient with phone number already exists.");
        }

        patientMapper.updateEntity(patient, patientRequest);

        Patient updatedPatient = patientRepository.save(patient);

        log.info("Patient updated successfully with ID: {}", updatedPatient.getId());

        return patientMapper.toResponse(updatedPatient);
    }

    @Override
    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId));

        return patientMapper.toResponse(patient);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {

        return patientRepository.findAllByOrderByFirstNameAscLastNameAsc()
                .stream()
                .map(patientMapper::toResponse)
                .toList();
    }

    @Override
    public void deletePatient(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId));

        patientRepository.delete(patient);

        log.info("Patient deleted successfully with ID: {}", patientId);
    }
}