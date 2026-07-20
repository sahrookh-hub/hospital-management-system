package com.shaarky.hms.repository;

import com.shaarky.hms.entity.Appointment;
import com.shaarky.hms.entity.Doctor;
import com.shaarky.hms.entity.Patient;
import com.shaarky.hms.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findAllByDoctorOrderByAppointmentDateAscAppointmentTimeAsc(
            Doctor doctor
    );

    List<Appointment> findAllByPatientOrderByAppointmentDateDescAppointmentTimeDesc(
            Patient patient
    );

    List<Appointment> findAllByAppointmentDateOrderByAppointmentTimeAsc(
            LocalDate appointmentDate
    );

    List<Appointment> findAllByDoctorAndAppointmentDateOrderByAppointmentTimeAsc(
            Doctor doctor,
            LocalDate appointmentDate
    );

    List<Appointment> findAllByStatusOrderByAppointmentDateAscAppointmentTimeAsc(
            AppointmentStatus status
    );

    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(
            Doctor doctor,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );

    boolean existsByPatientAndAppointmentDateAndAppointmentTime(
            Patient patient,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );

    boolean existsByDoctorAndAppointmentDateAndAppointmentTimeAndIdNot(
            Doctor doctor,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            Long id
    );

    boolean existsByPatientAndAppointmentDateAndAppointmentTimeAndIdNot(
            Patient patient,
            LocalDate appointmentDate,
            LocalTime appointmentTime,
            Long id
    );
}