package com.example.DepartmentManagement.Repository.Impl;

import com.example.DepartmentManagement.DBContext.DbConnection;
import com.example.DepartmentManagement.Model.Employee;
import com.example.DepartmentManagement.Model.FormerEmployee;
import com.example.DepartmentManagement.Model.Pos;
import com.example.DepartmentManagement.Repository.FomerEmployeeRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Date;

@Repository
public class FormerEmployeeRepositoryImpl implements FomerEmployeeRepository {

    private static final String SELECT_BY_PASSPORT = "SELECT id, former_employee_name, birth_date, address, passport_number FROM former_employee WHERE passport_number = ?";
    private static final String INSERT_FORMER_EMPLOYEE = "INSERT INTO former_employee (former_employee_name, birth_date, address, passport_number) VALUES (?, ?, ?,?)";

    private DbConnection databaseConnection;

    public FormerEmployeeRepositoryImpl() {
        this.databaseConnection = DbConnection.getInstance();
    }

    private FormerEmployee mapResultSetToFommer(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String formerEmployeeName = rs.getString("former_employee_name");
        Date birthDate = rs.getDate("birth_date");
        String address = rs.getString("address");
        String passport_number = rs.getString("passport_number");
        return new FormerEmployee(id,formerEmployeeName,birthDate,address, passport_number);
    }

    @Override
    public FormerEmployee findByPass(String passportNumber) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_PASSPORT)) {
            stmt.setString(1, passportNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToFommer(rs);
            } else {
                System.out.println("No passport number: " + passportNumber);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public FormerEmployee save(FormerEmployee formerEmployee) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_FORMER_EMPLOYEE,  Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, formerEmployee.getName());
            stmt.setDate(2, new java.sql.Date(formerEmployee.getBirthDate().getTime()));
            stmt.setString(3, formerEmployee.getAddress());
            stmt.setString(4, formerEmployee.getPassport_number());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Faild");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    formerEmployee.setId(id);
                    return formerEmployee;
                } else {
                    throw new SQLException("Faild");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}