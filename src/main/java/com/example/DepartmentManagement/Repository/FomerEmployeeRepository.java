package com.example.DepartmentManagement.Repository;

import com.example.DepartmentManagement.Model.FormerEmployee;

public interface FomerEmployeeRepository {
    FormerEmployee findByPass( String passportNumber);
    FormerEmployee save(FormerEmployee formerEmployee);
}
