package com.example.DepartmentManagement.Model;
import com.example.DepartmentManagement.Annotation.Column;
import com.example.DepartmentManagement.Annotation.Id;
import com.example.DepartmentManagement.Annotation.Table;
@Table(tableName = "senior_employee")
public class SeniorEmployee {
    @Id(name="id")
    private int id;
    @Column(columnName = "employee_id")
    private int employeeId;
    @Column(columnName = "department_id")
    private int departmentId;
    @Column(columnName = "position")
    private Pos position;

    public SeniorEmployee() {
    }

    public SeniorEmployee(int id, int employeeId, int departmentId, Pos position) {
        this.id = id;
        this.employeeId = employeeId;
        this.departmentId = departmentId;
        this.position = position;
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
