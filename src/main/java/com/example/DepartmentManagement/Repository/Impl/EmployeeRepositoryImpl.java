package com.example.DepartmentManagement.Repository.Impl;

import com.example.DepartmentManagement.DBContext.DbConnection;
import com.example.DepartmentManagement.Dto.EmployeeDto;
import com.example.DepartmentManagement.Model.Employee;
import com.example.DepartmentManagement.Model.Pos;
import com.example.DepartmentManagement.Repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String SELECT_BY_ID = "SELECT id, employee_name, birth_date, address, department_id,passport_number, basic_salary, net_salary, insuranceRate, position, status FROM employee WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, employee_name, birth_date, address, department_id, basic_salary, net_salary, insuranceRate, position, status FROM employee";
    private static final String INSERT_EMPLOYEE = "INSERT INTO employee (employee_name, birth_date, address, department_id,passport_number, basic_salary, net_salary, insuranceRate, position, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE employee SET employee_name = ?, birth_date = ?, address = ?, department_id = ?, passport_number = ?, basic_salary = ?, net_salary = ?, insuranceRate = ?, position = ?, status = ? WHERE id = ?";
    private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employee LIMIT ? OFFSET ?";
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final String UPDATE_EMPLOYEE_STATUS = "UPDATE employee SET status = ? WHERE id = ?";


    private DbConnection databaseConnection;

    public EmployeeRepositoryImpl() {
        this.databaseConnection = DbConnection.getInstance();
    }

    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("employee_name");
        java.util.Date birthDate = rs.getDate("birth_date");
        String address = rs.getString("address");
        int departmentId = rs.getInt("department_id");
        String passport_number = rs.getString("passport_number");
        double basicSalary = rs.getDouble("basic_salary");
        double netSalary = rs.getDouble("net_salary");
        double insuranceRate = rs.getDouble("insuranceRate");
        Pos position = Pos.valueOf(rs.getString("position"));
        String status = rs.getString("status");

        return new Employee(id, name, birthDate, address, departmentId,passport_number, basicSalary, netSalary, insuranceRate, position, status);
    }

    @Override
    public Employee findById(int id) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToEmployee(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employee> findAll(int page) {
        int size = 10;
        int offset = (page - 1) * size;

        List<Employee> employees = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_EMPLOYEES)) {
            stmt.setInt(1, size);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Employee employee = mapResultSetToEmployee(rs);
                employees.add(employee);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee save(Employee employee) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_EMPLOYEE, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, employee.getName());
            stmt.setDate(2, new java.sql.Date(employee.getBirthDate().getTime()));
            stmt.setString(3, employee.getAddress());
            stmt.setInt(4, employee.getDepartmentId());
            stmt.setString(5, employee.getPassport_number());
            stmt.setDouble(6, employee.getBasicSalary());
            stmt.setDouble(7, employee.getNetSalary());
            stmt.setDouble(8, employee.getInsuranceRate());
            stmt.setString(9, employee.getPosition().toString());
            stmt.setString(10, "On");

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Fail");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Fail");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error" + ex.getMessage());
        }
        return employee;
    }



    @Override
    public Employee update(Employee employee) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_EMPLOYEE)) {
            stmt.setString(1, employee.getName());
            stmt.setDate(2, new java.sql.Date(employee.getBirthDate().getTime()));
            stmt.setString(3, employee.getAddress());
            stmt.setInt(4, employee.getDepartmentId());
            stmt.setString(5, employee.getPassport_number());
            stmt.setDouble(6, employee.getBasicSalary());
            stmt.setDouble(7, employee.getNetSalary());
            stmt.setDouble(8, employee.getInsuranceRate());
            stmt.setString(9, employee.getPosition().toString());
            stmt.setString(10, employee.getStatus());
            stmt.setInt(11, employee.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Fail");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return employee;
    }
    @Override
    public String delete(int id) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_EMPLOYEE_STATUS)) {
            stmt.setString(1, "Off");
            stmt.setInt(2, id);


            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return "Fail";
            }
            return "Deleted successfully.";
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error" + id + ": " + ex.getMessage();
        }
    }
}
