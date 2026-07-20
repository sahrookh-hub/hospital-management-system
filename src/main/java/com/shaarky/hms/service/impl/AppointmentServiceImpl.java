package com.shaarky.hms.service.impl;

import com.shaarky.hms.dto.request.AppointmentRequest;
import com.shaarky.hms.dto.response.AppointmentResponse;
import com.shaarky.hms.entity.Appointment;
import com.shaarky.hms.entity.Doctor;
import com.shaarky.hms.entity.Patient;
import com.shaarky.hms.enums.AppointmentStatus;
import com.shaarky.hms.exception.DuplicateResourceException;
import com.shaarky.hms.exception.ResourceNotFoundException;
import com.shaarky.hms.mapper.AppointmentMapper;
import com.shaarky.hms.repository.AppointmentRepository;
import com.shaarky.hms.repository.DoctorRepository;
import com.shaarky.hms.repository.PatientRepository;
import com.shaarky.hms.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest) {

        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: "
                                        + appointmentRequest.getDoctorId()));

        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: "
                                        + appointmentRequest.getPatientId()));

        if (appointmentRequest.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Appointment date cannot be in the past."
            );
        }

        if (appointmentRequest.getAppointmentDate().isEqual(LocalDate.now())
                && appointmentRequest.getAppointmentTime().isBefore(LocalTime.now())) {

            throw new IllegalArgumentException(
                    "Appointment time cannot be in the past."
            );
        }

        if (appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(
                doctor,
                appointmentRequest.getAppointmentDate(),
                appointmentRequest.getAppointmentTime())) {

            throw new DuplicateResourceException(
                    "Doctor already has an appointment at the selected date and time."
            );
        }

        if (appointmentRepository.existsByPatientAndAppointmentDateAndAppointmentTime(
                patient,
                appointmentRequest.getAppointmentDate(),
                appointmentRequest.getAppointmentTime())) {

            throw new DuplicateResourceException(
                    "Patient already has an appointment at the selected date and time."
            );
        }

        Appointment appointment = appointmentMapper.toEntity(
                appointmentRequest,
                doctor,
                patient
        );

        Appointment savedAppointment = appointmentRepository.save(appointment);

        log.info("Appointment created successfully with ID: {}",
                savedAppointment.getId());

        return appointmentMapper.toResponse(savedAppointment);
    }

    @Override
    public AppointmentResponse updateAppointment(
            Long appointmentId,
            AppointmentRequest appointmentRequest) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: "
                                        + appointmentId));

        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: "
                                        + appointmentRequest.getDoctorId()));

        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: "
                                        + appointmentRequest.getPatientId()));

        if (appointmentRequest.getAppointmentDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Appointment date cannot be in the past."
            );
        }

        if (appointmentRequest.getAppointmentDate().isEqual(LocalDate.now())
                && appointmentRequest.getAppointmentTime().isBefore(LocalTime.now())) {

            throw new IllegalArgumentException(
                    "Appointment time cannot be in the past."
            );
        }

        if (appointmentRepository
                .existsByDoctorAndAppointmentDateAndAppointmentTimeAndIdNot(
                        doctor,
                        appointmentRequest.getAppointmentDate(),
                        appointmentRequest.getAppointmentTime(),
                        appointmentId)) {

            throw new DuplicateResourceException(
                    "Doctor already has another appointment at the selected date and time."
            );
        }

        if (appointmentRepository
                .existsByPatientAndAppointmentDateAndAppointmentTimeAndIdNot(
                        patient,
                        appointmentRequest.getAppointmentDate(),
                        appointmentRequest.getAppointmentTime(),
                        appointmentId)) {

            throw new DuplicateResourceException(
                    "Patient already has another appointment at the selected date and time."
            );
        }

        appointmentMapper.updateEntity(
                appointment,
                appointmentRequest,
                doctor,
                patient
        );

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        log.info("Appointment updated successfully with ID: {}",
                updatedAppointment.getId());

        return appointmentMapper.toResponse(updatedAppointment);
    }
    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: "
                                        + appointmentId));

        return appointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {

        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: "
                                        + doctorId));

        return appointmentRepository
                .findAllByDoctorOrderByAppointmentDateAscAppointmentTimeAsc(doctor)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByPatient(Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with id: "
                                        + patientId));

        return appointmentRepository
                .findAllByPatientOrderByAppointmentDateDescAppointmentTimeDesc(patient)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByStatus(String status) {

        AppointmentStatus appointmentStatus =
                AppointmentStatus.valueOf(status.toUpperCase());

        return appointmentRepository
                .findAllByStatusOrderByAppointmentDateAscAppointmentTimeAsc(
                        appointmentStatus
                )
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with id: "
                                        + appointmentId));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Completed appointments cannot be deleted."
            );
        }

        appointmentRepository.delete(appointment);

        log.info("Appointment deleted successfully with ID: {}",
                appointmentId);
    }

}