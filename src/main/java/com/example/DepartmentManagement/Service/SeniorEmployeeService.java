package com.example.DepartmentManagement.Service;

import com.example.DepartmentManagement.Dto.SeniorEmployeeDto;
import com.example.DepartmentManagement.Model.Pos;

public interface SeniorEmployeeService {
    int countManagers(int departmentId);
    int countDeputies(int departmentId);
    boolean canAddEmployee(int departmentId, Pos newPosition);
    void delete(int employeeId);
}
