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
        String addString = "INSERT INTO Users (email, password, name, surname, PhoneNumber, RoleId) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            addUser = connection.prepareStatement(addString);
            addUser.setString(1, user.getName());
            addUser.setString(2, user.getSurname());
            addUser.setString(3, user.getEmail());
            addUser.setString(4, user.getPassword());
            addUser.setString(5, user.getPhoneNumber());
            addUser.setInt(6, user.getRole());
            addUser.executeUpdate();
            addUser.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(String table, String id) {
        String query = String.format("DELETE FROM %s WHERE UserId = %s;", table, id);

        connect();
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editMentorData(String mentorsId, String column, String newValue) {
        update("Users", mentorsId, column, newValue);
    }
}
