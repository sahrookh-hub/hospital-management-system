package com.shaarky.hms.mapper;

import com.shaarky.hms.dto.request.AppointmentRequest;
import com.shaarky.hms.dto.response.AppointmentResponse;
import com.shaarky.hms.entity.Appointment;
import com.shaarky.hms.entity.Doctor;
import com.shaarky.hms.entity.Patient;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public Appointment toEntity(
            AppointmentRequest request,
            Doctor doctor,
            Patient patient
    ) {

        if (request == null) {
            return null;
        }

        Appointment appointment = Appointment.builder()
                .appointmentDate(request.getAppointmentDate())
                .appointmentTime(request.getAppointmentTime())
                .reason(request.getReason())
                .status(request.getStatus())
                .build();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        return appointment;
    }

    public AppointmentResponse toResponse(Appointment appointment) {

        if (appointment == null) {
            return null;
        }

        return AppointmentResponse.builder()
                .id(appointment.getId())
                .doctorId(appointment.getDoctor().getId())
                .doctorName(appointment.getDoctor().getFullName())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getFullName())
                .appointmentDate(appointment.getAppointmentDate())
                .appointmentTime(appointment.getAppointmentTime())
                .reason(appointment.getReason())
                .status(appointment.getStatus())
                .createdAt(appointment.getCreatedAt())
                .updatedAt(appointment.getUpdatedAt())
                .build();
    }

    public void updateEntity(
            Appointment appointment,
            AppointmentRequest request,
            Doctor doctor,
            Patient patient
    ) {

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());
        appointment.setStatus(request.getStatus());
    }
}