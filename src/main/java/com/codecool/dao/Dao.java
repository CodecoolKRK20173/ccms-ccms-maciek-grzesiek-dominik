package com.codecool.dao;

import com.codecool.models.User;

import java.sql.*;

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

    public void update(String table, String id, String column, String newValue) { //TODO change name to updateUser and maybe move that
        if (column.toLowerCase().equals("UserId")) {
            System.out.println("You can't change id");
            return;
        }
        String query = String.format("UPDATE %s SET %s = '%s' WHERE UserId = %s;", table, column, newValue, id);

        connect();
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User createUser(ResultSet results) throws SQLException{
        int userId = results.getInt("UserId");
        String name = results.getString("name");
        String surname = results.getString("Surname");
        String password = results.getString("Password");
        String email = results.getString("Email");
        String phoneNumber = results.getString("PhoneNumber");
        int role = results.getInt("RoleId");
        int classId = results.getInt("ClassId");
        return new User(userId, name, surname, password, email, phoneNumber, role, classId);
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