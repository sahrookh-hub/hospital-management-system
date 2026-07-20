package com.shaarky.hms.repository;

import com.shaarky.hms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByIdAndDeletedFalse(Long id);

    Optional<Department> findByNameAndDeletedFalse(String name);

    boolean existsByNameAndDeletedFalse(String name);

    List<Department> findAllByDeletedFalseOrderByNameAsc();
}