package com.example.DepartmentManagement.Common.Impl;

import com.example.DepartmentManagement.Annotation.Table;
import com.example.DepartmentManagement.Common.Generic;
import com.example.DepartmentManagement.Common.SqlQueryConstants;
import com.example.DepartmentManagement.DBContext.DbConnection;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class TImpl<T, ID> implements Generic<T, ID> {

    private Class<T> clazz;
    private String tableName;
    private Field[] entityFields;

    public TImpl(Class<T> clazz) {
        this.clazz = clazz;
        this.tableName = determineTableName(clazz);
        this.entityFields = clazz.getDeclaredFields();
        System.err.println("Class name: " + clazz.getSimpleName());
        System.err.println("Table name: " + this.tableName);
        System.err.println("Fields: " + entityFields.length);
    }

    private String determineTableName(Class<T> clazz) {
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        return tableAnnotation != null ? tableAnnotation.tableName() : clazz.getSimpleName();
    }

    public abstract T mapRow(ResultSet rs) throws SQLException;

    @Override
    public List<T> findAll() {
        String sql = SqlQueryConstants.SQL_SELECT_ALL.replace("%table_name%", this.tableName);
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            List<T> resultList = new ArrayList<>();
            while (rs.next()) {
                T entity = mapRow(rs);
                resultList.add(entity);
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while executing findAll", e);
        }
    }

    @Override
    public Optional<T> findById(Number id) {
        String sql = SqlQueryConstants.SQL_SELECT_BY_ID.replace("%table_name%", this.tableName);
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    T entity = mapRow(rs);
                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while executing findById", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<T> findByName(String name) {
        String sql = SqlQueryConstants.SQL_SELECT_BY_NAME.replace("%table_name%", this.tableName);
        try (Connection conn = DbConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    T entity = mapRow(rs);
                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while executing findByName", e);
        }
        return Optional.empty();
    }
}
