package com.shaarky.hms.service;

import com.shaarky.hms.dto.request.AppointmentRequest;
import com.shaarky.hms.dto.response.AppointmentResponse;

import java.util.List;

public interface AppointmentService {

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest);

    AppointmentResponse updateAppointment(Long appointmentId, AppointmentRequest appointmentRequest);

    AppointmentResponse getAppointmentById(Long appointmentId);

    List<AppointmentResponse> getAllAppointments();

    List<AppointmentResponse> getAppointmentsByDoctor(Long doctorId);

    List<AppointmentResponse> getAppointmentsByPatient(Long patientId);

    List<AppointmentResponse> getAppointmentsByStatus(String status);

    void deleteAppointment(Long appointmentId);
}