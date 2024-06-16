package com.example.DepartmentManagement.Repository;

import com.example.DepartmentManagement.Dto.DepartmentDto;
import com.example.DepartmentManagement.Model.Department;

import java.util.List;


public interface  DepartmentRepository {
    Department findById(int id);
    List<Department> findAll();
    Department save(Department department);
    Department update(Department department);
    String delete(int id);
    void updateEmployeeCount(int departmentId, int incrementBy);
}
