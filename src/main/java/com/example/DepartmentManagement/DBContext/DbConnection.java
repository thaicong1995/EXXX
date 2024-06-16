package com.example.DepartmentManagement.DBContext;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DbConnection {
    private static DbConnection instance;
    private final List<Connection> connections;
    private static final int POOL_SIZE = 10;

    private static final String URL = "jdbc:mysql://localhost:3306/company";
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12#@12";

    private DbConnection() {
        connections = new ArrayList<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                Class.forName(DRIVER_CLASS_NAME);
                Connection newConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                connections.add(newConnection);
            } catch (ClassNotFoundException | SQLException ex) {
                System.err.println("Exception: " + ex.getMessage());
                throw new RuntimeException("Failed to initialize database connections", ex);
            }
        }
    }

    public static synchronized DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        if (connections.isEmpty()) {
            if (connections.size() < POOL_SIZE) {
                try {
                    Class.forName(DRIVER_CLASS_NAME);
                    Connection newConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    return newConnection;
                } catch (ClassNotFoundException | SQLException ex) {
                    System.err.println("Exception: " + ex.getMessage());
                    throw new RuntimeException("Failed to create new database connection", ex);
                }
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections");
            }
        } else {
            return connections.remove(connections.size() - 1);
        }
    }

    public synchronized void returnConnection(Connection connection) {
        if (connection != null) {
            connections.add(connection);
        }
    }
}
