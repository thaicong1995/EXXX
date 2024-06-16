package com.example.DepartmentManagement.Model;
import ch.qos.logback.core.status.Status;
import com.example.DepartmentManagement.Annotation.Column;
import com.example.DepartmentManagement.Annotation.Id;
import com.example.DepartmentManagement.Annotation.Table;
import java.util.Date;
@Table(tableName = "employee")
public class Employee {
    @Id(name =  "id")
    private int id;
    @Column(columnName = "employee_name" )
    private String name;
    @Column(columnName = "birth_date" )
    private Date birthDate;
    @Column(columnName = "address" )
    private String address;
    @Column(columnName = "department_id" )
    private int departmentId;
    @Column(columnName = "passport_number")
    private String passport_number;
    @Column(columnName = "basic_salary" )
    private double basicSalary;
    @Column(columnName = "net_salary" )
    private double netSalary;
    @Column(columnName = "insuranceRate" )
    private double insuranceRate;
    @Column(columnName = "position" )
    private Pos position;
    @Column(columnName = "status" )
    private String status;

    public Employee() {
    }

    public Employee(int id, String name, Date birthDate, String address, int departmentId,String passport_number, double basicSalary, double netSalary, double insuranceRate, Pos position, String status) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.departmentId = departmentId;
        this.passport_number = passport_number;
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

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
    }
}
