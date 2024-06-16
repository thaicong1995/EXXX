package com.example.DepartmentManagement.Controller;

import com.example.DepartmentManagement.Dto.EmployeeDto;
import com.example.DepartmentManagement.Model.Employee;
import com.example.DepartmentManagement.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/page/{page}")
    public List<Employee> getEmployeesByPage(@PathVariable(value = "page") int page) {
        return employeeService.findAll(page);
    }

    @PostMapping("/add/{id}")
    public Employee createEmployee(@PathVariable("id") int departmentId, @RequestBody EmployeeDto employeeDto) {
        try {
            Employee savedEmployee = employeeService.save(departmentId, employeeDto);
            return savedEmployee;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/{de_id}")
    public Employee updateEmployee(@PathVariable("de_id") int de_id,
                                                      @RequestBody EmployeeDto employeeDto) {
        try {
            Employee updatedEmployee = employeeService.update(de_id, employeeDto);
            return updatedEmployee;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("del/{de_id}")
    public String deleteEmployee(@PathVariable("de_id") int de_id,  @RequestParam("ep_id") int ep_id) {
        try {
            String deletedEmployee = employeeService.delete(de_id, ep_id);
            return deletedEmployee;
        }catch (IllegalArgumentException ex) {
            return ex.getMessage();
        }
    }

}