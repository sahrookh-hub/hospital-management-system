package com.shaarky.hms.repository;

import com.shaarky.hms.entity.Department;
import com.shaarky.hms.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmployeeId(String employeeId);

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    List<Doctor> findAllByDepartment(Department department);

    List<Doctor> findAllByAvailableTrue();

    List<Doctor> findAllByDepartmentAndAvailableTrue(Department department);

    boolean existsByEmployeeId(String employeeId);

    boolean existsByEmail(String email);

    boolean existsByLicenseNumber(String licenseNumber);

    List<Doctor> findAllByOrderByFirstNameAscLastNameAsc();
}