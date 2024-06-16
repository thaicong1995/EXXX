package com.example.DepartmentManagement.Service.Impl;

import com.example.DepartmentManagement.Dto.DepartmentDto;
import com.example.DepartmentManagement.Mapper.Mapper;
import com.example.DepartmentManagement.Model.Department;
import com.example.DepartmentManagement.Repository.DepartmentRepository;
import com.example.DepartmentManagement.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final Mapper<Department, DepartmentDto> departmentMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, Mapper<Department, DepartmentDto> departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentRepository.findById(id);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department updateDepartment(int id, DepartmentDto departmentDto) {
        Department existingDepartment = departmentRepository.findById(id);
        if (existingDepartment == null) {
            throw new RuntimeException("Department not found: " + id);
        }
        Department departmentToUpdate = departmentMapper.mapDtoToEntity(departmentDto);
        departmentToUpdate.setId(id);
        return departmentRepository.update(departmentToUpdate);
    }

    @Override
    public String deleteDepartment(int id) {
        return departmentRepository.delete(id);
    }
}
