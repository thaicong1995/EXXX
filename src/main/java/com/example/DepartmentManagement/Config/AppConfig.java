package com.example.DepartmentManagement.Config;

import com.example.DepartmentManagement.Dto.DepartmentDto;
import com.example.DepartmentManagement.Dto.EmployeeDto;
import com.example.DepartmentManagement.Mapper.Mapper;
import com.example.DepartmentManagement.Model.Department;
import com.example.DepartmentManagement.Model.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Mapper<Department, DepartmentDto> departmentMapper() {
        return new Mapper<>(Department.class, DepartmentDto.class);
    }

    @Bean
    public Mapper<Employee, EmployeeDto> employeeMapper() {
        return new Mapper<>(Employee.class, EmployeeDto.class);
    }

}