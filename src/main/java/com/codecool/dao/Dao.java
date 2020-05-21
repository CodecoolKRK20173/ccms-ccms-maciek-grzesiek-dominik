package com.codecool.dao;

import com.codecool.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Dao {
    protected Connection connection;
    protected Statement statement;

    public static final String DB_NAME = "src/main/resources/DataBase.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CONNECTION_STRING);
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.getStackTrace();
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database" + e.getMessage());
        }
    }

    public void update(String table, String id, String column, String newValue) {
        if (column.toLowerCase().equals("id")) {
            System.out.println("You can't change id");
            return;
        }
        String query = String.format("UPDATE %s SET %s = %s WHERE Id = %s;", table, column, newValue, id);
//                "UPDATE " + table + " SET " + column + " = " + newValue + " WHERE Id = " + id + ";";

        connect();
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}