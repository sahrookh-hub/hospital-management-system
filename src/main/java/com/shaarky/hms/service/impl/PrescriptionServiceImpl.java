package com.shaarky.hms.service.impl;

import com.shaarky.hms.dto.request.PrescriptionRequest;
import com.shaarky.hms.dto.response.PrescriptionResponse;
import com.shaarky.hms.entity.Appointment;
import com.shaarky.hms.entity.Doctor;
import com.shaarky.hms.entity.Patient;
import com.shaarky.hms.entity.Prescription;
import com.shaarky.hms.enums.AppointmentStatus;
import com.shaarky.hms.exception.DuplicateResourceException;
import com.shaarky.hms.exception.ResourceNotFoundException;
import com.shaarky.hms.mapper.PrescriptionMapper;
import com.shaarky.hms.repository.AppointmentRepository;
import com.shaarky.hms.repository.DoctorRepository;
import com.shaarky.hms.repository.PatientRepository;
import com.shaarky.hms.repository.PrescriptionRepository;
import com.shaarky.hms.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PrescriptionMapper prescriptionMapper;

    @Override
    public PrescriptionResponse createPrescription(PrescriptionRequest request) {

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + request.getAppointmentId()));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Prescription can only be created for completed appointments.");
        }

        if (prescriptionRepository.existsByAppointment(appointment)) {
            throw new DuplicateResourceException(
                    "Prescription already exists for this appointment.");
        }

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + request.getDoctorId()));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalArgumentException(
                    "Doctor does not belong to this appointment.");
        }

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + request.getPatientId()));

        if (!appointment.getPatient().getId().equals(patient.getId())) {
            throw new IllegalArgumentException(
                    "Patient does not belong to this appointment.");
        }

        String prescriptionNumber = generatePrescriptionNumber();

        while (prescriptionRepository.existsByPrescriptionNumber(prescriptionNumber)) {
            prescriptionNumber = generatePrescriptionNumber();
        }

        Prescription prescription = prescriptionMapper.toEntity(
                request,
                appointment,
                doctor,
                patient,
                prescriptionNumber
        );

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        log.info(
                "Prescription created successfully with ID: {}",
                savedPrescription.getId()
        );

        return prescriptionMapper.toResponse(savedPrescription);
    }

    private String generatePrescriptionNumber() {

        return "RX-"
                + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }
    @Override
    public PrescriptionResponse updatePrescription(
            Long prescriptionId,
            PrescriptionRequest request) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription not found with id: " + prescriptionId));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + request.getAppointmentId()));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Prescription can only be created for completed appointments.");
        }

        if (!prescription.getAppointment().getId().equals(appointment.getId())
                && prescriptionRepository.existsByAppointment(appointment)) {
            throw new DuplicateResourceException(
                    "Prescription already exists for this appointment.");
        }

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + request.getDoctorId()));

        if (!appointment.getDoctor().getId().equals(doctor.getId())) {
            throw new IllegalArgumentException(
                    "Doctor does not belong to this appointment.");
        }

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + request.getPatientId()));

        if (!appointment.getPatient().getId().equals(patient.getId())) {
            throw new IllegalArgumentException(
                    "Patient does not belong to this appointment.");
        }

        prescriptionMapper.updateEntity(
                prescription,
                request,
                appointment,
                doctor,
                patient
        );

        Prescription updatedPrescription = prescriptionRepository.save(prescription);

        log.info(
                "Prescription updated successfully with ID: {}",
                updatedPrescription.getId()
        );

        return prescriptionMapper.toResponse(updatedPrescription);
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionById(Long prescriptionId) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription not found with id: " + prescriptionId));

        return prescriptionMapper.toResponse(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getAllPrescriptions() {

        return prescriptionRepository.findAll()
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with id: " + doctorId));

        return prescriptionRepository.findAllByDoctorOrderByCreatedAtDesc(doctor)
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByPatient(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Patient not found with id: " + patientId));

        return prescriptionRepository.findAllByPatientOrderByCreatedAtDesc(patient)
                .stream()
                .map(prescriptionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescriptionByAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + appointmentId));

        Prescription prescription = prescriptionRepository.findByAppointment(appointment)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription not found for appointment id: " + appointmentId));

        return prescriptionMapper.toResponse(prescription);
    }

    @Override
    public void deletePrescription(Long prescriptionId) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Prescription not found with id: " + prescriptionId));

        prescriptionRepository.delete(prescription);

        log.info("Prescription deleted successfully with ID: {}", prescriptionId);
    }
}