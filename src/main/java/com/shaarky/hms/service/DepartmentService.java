package com.shaarky.hms.service;

import com.shaarky.hms.dto.request.DepartmentRequest;
import com.shaarky.hms.entity.Department;

import java.util.List;

public interface DepartmentService {

    Department create(DepartmentRequest request);

    Department update(Long id, DepartmentRequest request);

    Department findById(Long id);

    List<Department> findAll();

    void softDelete(Long id);
}