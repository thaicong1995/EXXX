package com.example.DepartmentManagement.Service.Impl;

import com.example.DepartmentManagement.Dto.DepartmentDto;
import com.example.DepartmentManagement.Dto.EmployeeDto;
import com.example.DepartmentManagement.Dto.SeniorEmployeeDto;
import com.example.DepartmentManagement.Mapper.Mapper;
import com.example.DepartmentManagement.Model.*;
import com.example.DepartmentManagement.Repository.DepartmentRepository;
import com.example.DepartmentManagement.Repository.EmployeeRepository;
import com.example.DepartmentManagement.Repository.FomerEmployeeRepository;
import com.example.DepartmentManagement.Repository.SeniorEmployeeRepository;
import com.example.DepartmentManagement.Service.EmployeeService;
import com.example.DepartmentManagement.Service.SeniorEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceIpml implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final SeniorEmployeeRepository seniorEmployeeRepository;
    private final FomerEmployeeRepository fomerEmployeeRepository;
    private final SeniorEmployeeService seniorEmployeeService;
    private final Mapper<Employee, EmployeeDto> employeeMapper;
    @Autowired
    public EmployeeServiceIpml(SeniorEmployeeService seniorEmployeeService, Mapper<Employee, EmployeeDto> employeeMapper,
                               EmployeeRepository employeeRepository, DepartmentRepository departmentRepository,
                               SeniorEmployeeRepository seniorEmployeeRepository, FomerEmployeeRepository fomerEmployeeRepository) {
        this.seniorEmployeeService = seniorEmployeeService;
        this.employeeMapper = employeeMapper;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.seniorEmployeeRepository = seniorEmployeeRepository;
        this.fomerEmployeeRepository = fomerEmployeeRepository;
    }


    public Employee save(int departmentId, EmployeeDto employeeDto) {
        Department department = departmentRepository.findById(departmentId);
        if (department == null) {
            throw new IllegalArgumentException("Department " + departmentId );
        }
        FormerEmployee formerEmployee = fomerEmployeeRepository.findByPass(employeeDto.getPassport_number());
        if (formerEmployee != null) {
            throw new IllegalArgumentException("Employee " + employeeDto.getPassport_number() );
        }

        Pos newPosition = employeeDto.getPosition();

        if (!seniorEmployeeService.canAddEmployee(departmentId, newPosition)) {
            throw new RuntimeException("Maximum managers or durty");
        }


        Employee employee = employeeMapper.mapDtoToEntity(employeeDto);
        employee.setDepartmentId(departmentId);

        Employee savedEmployee = employeeRepository.save(employee);

        departmentRepository.updateEmployeeCount(departmentId, 1);

        if (savedEmployee.getPosition() == Pos.Manager || savedEmployee.getPosition() == Pos.Deputy) {
            seniorEmployeeRepository.save(new SeniorEmployee(
                    0,
                    savedEmployee.getId(),
                    departmentId,
                    savedEmployee.getPosition()
            ));
        }

        return savedEmployee;
    }

    @Override
    public Employee update(int de_id, EmployeeDto employeeDto) {
        Department department = departmentRepository.findById(de_id);
        if (department == null) {
            throw new IllegalArgumentException("Department "+ de_id );
        }

        Employee existingEmployee = employeeRepository.findById(employeeDto.getId());
        if (existingEmployee == null || existingEmployee.getStatus().equals("Off" )) {
            throw new IllegalArgumentException("Employee " + employeeDto.getId() );
        }

        Pos newPosition = employeeDto.getPosition();
        if (!seniorEmployeeService.canAddEmployee(de_id, newPosition)) {
            throw new RuntimeException("Cannot update to position " + newPosition);
        }

        Pos currentPosition = existingEmployee.getPosition();

        if ((currentPosition == Pos.Manager || currentPosition == Pos.Deputy) && newPosition != currentPosition) {
            seniorEmployeeRepository.delete(existingEmployee.getId());
        } else if ((newPosition == Pos.Manager || newPosition == Pos.Deputy) && currentPosition != newPosition) {
            seniorEmployeeRepository.save(new SeniorEmployee(
                    0,
                    existingEmployee.getId(),
                    de_id,
                    newPosition
            ));
        }
        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setBirthDate(employeeDto.getBirthDate());
        existingEmployee.setAddress(employeeDto.getAddress());
        existingEmployee.setDepartmentId(employeeDto.getDepartmentId());
        existingEmployee.setPassport_number(employeeDto.getPassport_number());
        existingEmployee.setBasicSalary(employeeDto.getBasicSalary());
        existingEmployee.setNetSalary(employeeDto.getNetSalary());
        existingEmployee.setInsuranceRate(employeeDto.getInsuranceRate());
        existingEmployee.setPosition(employeeDto.getPosition());
        existingEmployee.setStatus(employeeDto.getStatus());
        existingEmployee.setId(employeeDto.getId());
        employeeRepository.update(existingEmployee);
        return existingEmployee;
    }

    @Override
    public String delete(int de_id, int employee_id) {
        Department department = departmentRepository.findById(de_id);
        if (department == null) {
            return "Department" + de_id + " does not exist.";
        }
        Employee existingEmployee = employeeRepository.findById(employee_id);
        if (existingEmployee == null || existingEmployee.getStatus().equals("Off" )) {
            return "Employee " + employee_id + " does not exist.";
        }

        Pos currentPosition = existingEmployee.getPosition();
        if (currentPosition == Pos.Manager || currentPosition == Pos.Deputy){
            seniorEmployeeRepository.delete(existingEmployee.getId());
        }

        FormerEmployee formerEmployee = new FormerEmployee();
        formerEmployee.setName(existingEmployee.getName());
        formerEmployee.setBirthDate(existingEmployee.getBirthDate());
        formerEmployee.setAddress(existingEmployee.getAddress());
        formerEmployee.setPassport_number(existingEmployee.getPassport_number());
        fomerEmployeeRepository.save(formerEmployee);
        employeeRepository.delete(existingEmployee.getId());
        departmentRepository.updateEmployeeCount(de_id, -1);
        return "Successfully " + employee_id ;
    }

    @Override
    public List<Employee> findAll(int page) {
        return employeeRepository.findAll(page);
    }

}
