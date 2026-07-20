package com.shaarky.hms.service.impl;

import com.shaarky.hms.dto.request.DoctorRequest;
import com.shaarky.hms.dto.response.DoctorResponse;
import com.shaarky.hms.entity.Department;
import com.shaarky.hms.entity.Doctor;
import com.shaarky.hms.exception.DuplicateResourceException;
import com.shaarky.hms.exception.ResourceNotFoundException;
import com.shaarky.hms.mapper.DoctorMapper;
import com.shaarky.hms.repository.DepartmentRepository;
import com.shaarky.hms.repository.DoctorRepository;
import com.shaarky.hms.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorMapper doctorMapper;

    @Override
    public DoctorResponse createDoctor(DoctorRequest doctorRequest) {

        if (doctorRepository.existsByEmployeeId(doctorRequest.getEmployeeId())) {
            throw new DuplicateResourceException("Doctor with employee ID already exists.");
        }

        if (doctorRepository.existsByEmail(doctorRequest.getEmail())) {
            throw new DuplicateResourceException("Doctor with email already exists.");
        }

        if (doctorRepository.existsByLicenseNumber(doctorRequest.getLicenseNumber())) {
            throw new DuplicateResourceException("Doctor with license number already exists.");
        }

        Department department = departmentRepository.findById(doctorRequest.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: "
                                + doctorRequest.getDepartmentId()));

        Doctor doctor = doctorMapper.toEntity(doctorRequest, department);

        Doctor savedDoctor = doctorRepository.save(doctor);

        log.info("Doctor created successfully with ID: {}", savedDoctor.getId());

        return doctorMapper.toResponse(savedDoctor);
    }

    @Override
    public DoctorResponse updateDoctor(Long doctorId, DoctorRequest doctorRequest) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        if (!doctor.getEmployeeId().equals(doctorRequest.getEmployeeId())
                && doctorRepository.existsByEmployeeId(doctorRequest.getEmployeeId())) {
            throw new DuplicateResourceException("Doctor with employee ID already exists.");
        }

        if (!doctor.getEmail().equalsIgnoreCase(doctorRequest.getEmail())
                && doctorRepository.existsByEmail(doctorRequest.getEmail())) {
            throw new DuplicateResourceException("Doctor with email already exists.");
        }

        if (!doctor.getLicenseNumber().equals(doctorRequest.getLicenseNumber())
                && doctorRepository.existsByLicenseNumber(doctorRequest.getLicenseNumber())) {
            throw new DuplicateResourceException("Doctor with license number already exists.");
        }

        Department department = departmentRepository.findById(doctorRequest.getDepartmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: "
                                + doctorRequest.getDepartmentId()));

        doctorMapper.updateEntity(doctor, doctorRequest, department);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        log.info("Doctor updated successfully with ID: {}", updatedDoctor.getId());

        return doctorMapper.toResponse(updatedDoctor);
    }

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        return doctorMapper.toResponse(doctor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getAllDoctors() {

        return doctorRepository.findAllByOrderByFirstNameAscLastNameAsc()
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getDoctorsByDepartment(Long departmentId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id: " + departmentId));

        return doctorRepository.findAllByDepartment(department)
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DoctorResponse> getAvailableDoctors() {

        return doctorRepository.findAllByAvailableTrue()
                .stream()
                .map(doctorMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        doctorRepository.delete(doctor);

        log.info("Doctor deleted successfully with ID: {}", doctorId);
    }
}