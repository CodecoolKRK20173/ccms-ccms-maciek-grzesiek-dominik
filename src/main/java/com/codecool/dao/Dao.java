package com.codecool.dao;

import com.codecool.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Dao {
    protected Connection connection;
    protected Statement statement;

    public static final String DB_NAME = "src/main/resources/newShopDB.db";
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

    public void addUser(User user) {
        connect();
        PreparedStatement addUser;
        String addString = "INSERT INTO Users (name, surname, email, password, PhoneNumber, RoleId) VALUES (?, ?, ?, ?, ?)";
        try {
            addUser = connection.prepareStatement(addString);
            addUser.setString(1, user.getName());
            addUser.setString(1, user.getSurname());
            addUser.setString(2, user.getEmail());
            addUser.setString(3, user.getPassword());
            addUser.setString(4, user.getPhoneNumber());
            addUser.setInt(5, user.getRole());
            addUser.executeUpdate();
            addUser.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String table, String id) {
        String query = String.format("DELETE FROM %s WHERE Id = %s;", table, id);

        connect();
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}