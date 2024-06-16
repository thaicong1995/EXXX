package com.example.DepartmentManagement.Service.Impl;

import com.example.DepartmentManagement.Dto.SeniorEmployeeDto;
import com.example.DepartmentManagement.Model.Employee;
import com.example.DepartmentManagement.Model.Pos;
import com.example.DepartmentManagement.Model.SeniorEmployee;
import com.example.DepartmentManagement.Repository.SeniorEmployeeRepository;
import com.example.DepartmentManagement.Service.SeniorEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeniorEmployeeServiceIpml implements SeniorEmployeeService {
    private final SeniorEmployeeRepository seniorEmployeeRepository;

    @Autowired
    public SeniorEmployeeServiceIpml(SeniorEmployeeRepository seniorEmployeeRepository) {
        this.seniorEmployeeRepository = seniorEmployeeRepository;
    }

    @Override
    public int countManagers(int departmentId) {
        return seniorEmployeeRepository.countManagers(departmentId);
    }

    @Override
    public int countDeputies(int departmentId) {
        return seniorEmployeeRepository.countDeputies(departmentId);
    }

    @Override
    public boolean canAddEmployee(int departmentId, Pos newPosition) {
        int managersCount = countManagers(departmentId);
        int deputiesCount = countDeputies(departmentId);
        if (newPosition == Pos.Manager && managersCount >= 1) {
            return false;
        }

        if (newPosition == Pos.Deputy && deputiesCount >= 2) {
            return false;
        }

        return true;
    }

    @Override
    public void delete(int employeeId) {
        seniorEmployeeRepository.delete(employeeId);
    }



}
