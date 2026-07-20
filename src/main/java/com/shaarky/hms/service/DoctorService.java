package com.shaarky.hms.service;

import com.shaarky.hms.dto.request.DoctorRequest;
import com.shaarky.hms.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {

    DoctorResponse createDoctor(DoctorRequest doctorRequest);

    DoctorResponse updateDoctor(Long doctorId, DoctorRequest doctorRequest);

    DoctorResponse getDoctorById(Long doctorId);

    List<DoctorResponse> getAllDoctors();

    List<DoctorResponse> getDoctorsByDepartment(Long departmentId);

    List<DoctorResponse> getAvailableDoctors();

    void deleteDoctor(Long doctorId);
}