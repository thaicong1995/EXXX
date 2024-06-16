package com.example.DepartmentManagement.Dto;

import com.example.DepartmentManagement.Model.Pos;

public class SeniorEmployeeDto {
    private int id;
    private int employeeId;
    private int departmentId;
    private Pos position;

    public SeniorEmployeeDto() {
    }

    public SeniorEmployeeDto(int id, int employeeId, int departmentId, Pos position) {
        this.id = id;
        this.employeeId = employeeId;
        this.departmentId = departmentId;
        this.position = position;
    }

    public SeniorEmployeeDto(int id, int deId, Pos newPosition) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Pos getPosition() {
        return position;
    }

    public void setPosition(Pos position) {
        this.position = position;
    }
}
