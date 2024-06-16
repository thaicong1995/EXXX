package com.example.DepartmentManagement.Service;

import com.example.DepartmentManagement.Dto.DepartmentDto;
import com.example.DepartmentManagement.Model.Department;

import java.util.List;

public interface DepartmentService {
    Department getDepartmentById(int id);
    List<Department> getAllDepartments();
    Department createDepartment(Department department);
    Department updateDepartment(int Id, DepartmentDto departmentDto);
    String deleteDepartment(int id);

}
