package com.shaarky.hms.service.impl;

import com.shaarky.hms.dto.request.DepartmentRequest;
import com.shaarky.hms.entity.Department;
import com.shaarky.hms.exception.DuplicateResourceException;
import com.shaarky.hms.exception.ResourceNotFoundException;
import com.shaarky.hms.repository.DepartmentRepository;
import com.shaarky.hms.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Department create(DepartmentRequest request) {

        if (departmentRepository.existsByNameAndDeletedFalse(request.getName())) {
            throw new DuplicateResourceException(
                    "Department",
                    "name",
                    request.getName()
            );
        }

        Department department = new Department();
        department.setName(request.getName().trim());
        department.setDescription(request.getDescription());

        return departmentRepository.save(department);
    }

    @Override
    public Department update(Long id, DepartmentRequest request) {

        Department department = findById(id);

        departmentRepository.findByNameAndDeletedFalse(request.getName())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Department",
                            "name",
                            request.getName()
                    );
                });

        department.setName(request.getName().trim());
        department.setDescription(request.getDescription());

        return departmentRepository.save(department);
    }

    @Override
    @Transactional(readOnly = true)
    public Department findById(Long id) {

        return departmentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Department",
                                "id",
                                id
                        )
                );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Department> findAll() {
        return departmentRepository.findAllByDeletedFalseOrderByNameAsc();
    }

    @Override
    public void softDelete(Long id) {

        Department department = findById(id);
        department.setDeleted(true);

        departmentRepository.save(department);
    }
}