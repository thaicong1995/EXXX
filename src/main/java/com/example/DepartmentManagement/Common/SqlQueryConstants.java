package com.example.DepartmentManagement.Common;

public class SqlQueryConstants {
    public static final String SQL_SELECT_ALL = "select * from %table_name% limit 1000 offset 1;";
    public static final String SQL_SELECT_BY_ID = "SELECT * FROM %table_name% WHERE id = ?";
    public static final String SQL_SELECT_BY_NAME = "SELECT * FROM %table_name% WHERE name = ?";
}