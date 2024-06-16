package com.example.DepartmentManagement.Repository;

import com.example.DepartmentManagement.Model.SeniorEmployee;

public interface SeniorEmployeeRepository {
    int countManagers(int departmentId);
    SeniorEmployee findById(int id);
    int countDeputies(int departmentId);
    void save(SeniorEmployee seniorEmployee);
    void delete(int employeeId);
    void update(SeniorEmployee seniorEmployee);
}
