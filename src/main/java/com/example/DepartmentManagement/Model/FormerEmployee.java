package com.example.DepartmentManagement.Model;
import com.example.DepartmentManagement.Annotation.Column;
import com.example.DepartmentManagement.Annotation.Id;
import com.example.DepartmentManagement.Annotation.Table;
import java.util.Date;
@Table(tableName = "former_employee")
public class FormerEmployee {
    @Id(name =  "id")
    private int id;
    @Column(columnName = "former_employee_name" )
    private String name;
    @Column(columnName = "birth_date" )
    private Date birthDate;
    @Column(columnName = "address" )
    private String address;
    @Column(columnName = "passport_number")
    private String passport_number;

    public FormerEmployee() {
    }

    public FormerEmployee(int id, String name, Date birthDate, String address,String passport_number) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.address = address;
        this.passport_number = passport_number;
    }

    public String getPassport_number() {
        return passport_number;
    }

    public void setPassport_number(String passport_number) {
        this.passport_number = passport_number;
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
}
