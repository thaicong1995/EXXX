package com.example.DepartmentManagement.Repository;

import com.example.DepartmentManagement.Model.Employee;

import java.util.List;

public interface EmployeeRepository {
    Employee findById(int id);
    public List<Employee> findAll(int page);

    List<Employee> findAll();

    Employee save(Employee employee);

    Employee update(Employee employee);

    String delete(int id);
}

