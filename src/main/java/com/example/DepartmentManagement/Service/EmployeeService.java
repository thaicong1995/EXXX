package com.example.DepartmentManagement.Service;

import com.example.DepartmentManagement.Dto.EmployeeDto;
import com.example.DepartmentManagement.Model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee save(int departmentId,EmployeeDto employeeDto);
    Employee update(int de_id, EmployeeDto employeeDto);
    String delete(int de_id, int employee_id);
    List<Employee> findAll(int page);
}
