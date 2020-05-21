package com.codecool.dao;

import com.codecool.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDao extends Dao {

    public AdminDao() {
    }

    public void addMentor(User user) {
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
