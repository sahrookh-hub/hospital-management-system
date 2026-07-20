package com.shaarky.hms.repository;

import com.shaarky.hms.entity.Appointment;
import com.shaarky.hms.entity.Doctor;
import com.shaarky.hms.entity.Patient;
import com.shaarky.hms.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    Optional<Prescription> findByPrescriptionNumber(String prescriptionNumber);

    Optional<Prescription> findByAppointment(Appointment appointment);

    List<Prescription> findAllByDoctorOrderByCreatedAtDesc(Doctor doctor);

    List<Prescription> findAllByPatientOrderByCreatedAtDesc(Patient patient);

    boolean existsByPrescriptionNumber(String prescriptionNumber);

    boolean existsByAppointment(Appointment appointment);
}