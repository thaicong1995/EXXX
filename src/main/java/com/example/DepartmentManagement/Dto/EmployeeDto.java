package com.example.DepartmentManagement.Dto;

import com.example.DepartmentManagement.Model.Pos;

import java.util.Date;

public class EmployeeDto {
    private int id;
    private String name;
    private Date birthDate;
    private String address;
    private String passport_number;
    private int departmentId;
    private double basicSalary;
    private double netSalary;
    private double insuranceRate;
    private Pos position;
    private String status;
    public EmployeeDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public EmployeeDto(int id, String passport_number, String name, Date birthDate, String address, int departmentId, double basicSalary, double netSalary, double insuranceRate, Pos position, String status) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.passport_number = passport_number;
        this.departmentId = departmentId;
        this.basicSalary = basicSalary;
        this.netSalary = netSalary;
        this.insuranceRate = insuranceRate;
        this.position = position;
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getInsuranceRate() {
        return insuranceRate;
    }

    public void setInsuranceRate(double insuranceRate) {
        this.insuranceRate = insuranceRate;
    }

    public Pos getPosition() {
        return position;
    }

    public void setPosition(Pos position) {
        this.position = position;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }
}
