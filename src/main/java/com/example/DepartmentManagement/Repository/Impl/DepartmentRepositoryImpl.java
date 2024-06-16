package com.example.DepartmentManagement.Repository.Impl;

import com.example.DepartmentManagement.DBContext.DbConnection;
import com.example.DepartmentManagement.Dto.DepartmentDto;
import com.example.DepartmentManagement.Model.Department;
import com.example.DepartmentManagement.Repository.DepartmentRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private static final String SELECT_BY_ID = "SELECT id, department_name, count, room_number FROM department WHERE id = ?";
    private static final String SELECT_ALL = "SELECT id, department_name, count, room_number FROM department";
    private static final String INSERT_DEPARTMENT = "INSERT INTO department (department_name, count, room_number) VALUES (?, ?, ?)";
    private static final String UPDATE_DEPARTMENT = "UPDATE department SET department_name = ?, count = ?, room_number = ? WHERE id = ?";
    private static final String DELETE_DEPARTMENT = "DELETE FROM department WHERE id = ?";
    private static final String UPDATE_EMPLOYEE_COUNT = "UPDATE department SET count = count + 1 WHERE id = ?";


    private DbConnection databaseConnection;

    public DepartmentRepositoryImpl() {
        this.databaseConnection = DbConnection.getInstance();
    }

    private Department mapResultSetToDepartment(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("department_name");
        int employeeCount = rs.getInt("count");
        String roomNumber = rs.getString("room_number");

        return new Department(id, name, employeeCount, roomNumber);
    }

    @Override
    public Department findById(int id) {
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

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                departments.add(mapResultSetToDepartment(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return departments;
    }

    @Override
    public Department save(Department department) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_DEPARTMENT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getEmployeeCount());
            stmt.setString(3, department.getRoomNumber());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Create failed");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    department.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Create failed");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return department;
    }

    public Department update(Department department) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_DEPARTMENT)) {
            stmt.setString(1, department.getName());
            stmt.setInt(2, department.getEmployeeCount());
            stmt.setString(3, department.getRoomNumber());
            stmt.setInt(4, department.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return department;
    }

    @Override
    public String delete(int id) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_DEPARTMENT)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return "Deleted " + id + " failed";
            }
            return "Department" + id + " deleted";
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Delete ID " + id + ": " + ex.getMessage();
        }
    }

    @Override
    public void updateEmployeeCount(int departmentId, int incrementBy) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_EMPLOYEE_COUNT)) {
            stmt.setInt(1, departmentId);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Fail");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
