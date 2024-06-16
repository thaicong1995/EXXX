package com.example.DepartmentManagement.Repository.Impl;

import com.example.DepartmentManagement.DBContext.DbConnection;
import com.example.DepartmentManagement.Model.Pos;
import com.example.DepartmentManagement.Model.SeniorEmployee;
import com.example.DepartmentManagement.Repository.SeniorEmployeeRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class SeniorEmployeeRepositoryImpl implements SeniorEmployeeRepository {

    private static final String SELECT_BY_ID = "SELECT id, employee_id, department_id, position FROM senior_employee WHERE id = ?";
    private static final String INSERT_SENIOR_EMPLOYEE = "INSERT INTO senior_employee (employee_id, department_id, position) VALUES (?, ?, ?)";
    private static final String COUNT_MANAGERS_BY_DEPARTMENT_ID = "SELECT COUNT(*) FROM senior_employee WHERE department_id = ? AND position = 'Manager'";
    private static final String COUNT_DEPUTIES_BY_DEPARTMENT_ID = "SELECT COUNT(*) FROM senior_employee WHERE department_id = ? AND position = 'Deputy'";
    private static final String DELETE_SENIOR_EMPLOYEE = "DELETE FROM senior_employee WHERE employee_id  = ?";
    private static final String UPDATE_SENIOR_EMPLOYEE = "UPDATE senior_employee SET employee_id = ?, department_id = ?, position = ? WHERE id = ?";
    private DbConnection databaseConnection;

    public SeniorEmployeeRepositoryImpl() {
        this.databaseConnection = DbConnection.getInstance();
    }

    private SeniorEmployee mapResultSetToDepartment(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int employeeId = rs.getInt("employee_id");
        int departmentId = rs.getInt("department_id");
        Pos position = Pos.valueOf(rs.getString("position"));

        return new SeniorEmployee(id, employeeId, departmentId, position);
    }

    @Override
    public SeniorEmployee findById(int id) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToDepartment(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void update(SeniorEmployee seniorEmployee) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SENIOR_EMPLOYEE)) {
            stmt.setInt(1, seniorEmployee.getEmployeeId());
            stmt.setInt(2, seniorEmployee.getDepartmentId());
            stmt.setString(3, seniorEmployee.getPosition().toString());
            stmt.setInt(4, seniorEmployee.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Faild");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public int countManagers(int departmentId) {
        int count = 0;
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(COUNT_MANAGERS_BY_DEPARTMENT_ID)) {
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public int countDeputies(int departmentId) {
        int count = 0;
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(COUNT_DEPUTIES_BY_DEPARTMENT_ID)) {
            stmt.setInt(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public void save(SeniorEmployee seniorEmployee) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SENIOR_EMPLOYEE)) {
            stmt.setInt(1, seniorEmployee.getEmployeeId());
            stmt.setInt(2, seniorEmployee.getDepartmentId());
            stmt.setString(3, seniorEmployee.getPosition().toString());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Faild");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(int employeeId) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SENIOR_EMPLOYEE)) {
            stmt.setInt(1, employeeId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Faild");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
