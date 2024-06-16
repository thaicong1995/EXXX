package com.example.DepartmentManagement.Model;

import com.example.DepartmentManagement.Annotation.Column;
import com.example.DepartmentManagement.Annotation.Id;
import com.example.DepartmentManagement.Annotation.Table;

@Table(tableName = "department")
public class Department {
    @Id(name =  "id")
    private int id;
    @Column(columnName = "department_name" )
    private String name;
    @Column(columnName = "count" )
    private int employeeCount;
    @Column(columnName = "room_number" )
    private String roomNumber;
    public Department() {
    }

    public Department(int id, String name, int employeeCount, String roomNumber) {
        this.id = id;
        this.name = name;
        this.employeeCount = employeeCount;
        this.roomNumber = roomNumber;
    }

    public Department(String name, int employeeCount, String roomNumber) {
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

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
