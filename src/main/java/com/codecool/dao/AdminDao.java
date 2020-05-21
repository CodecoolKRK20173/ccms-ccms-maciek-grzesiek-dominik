package com.codecool.dao;

import com.codecool.models.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDao extends Dao {

    public AdminDao() {
    }

//    private User createUser() {
//        String name = io.gatherInput("New user's name: ");
//        String surname = io.gatherInput("New user's surname: ");
//        String email = io.gatherInput("New user's email: ");
//        String password = io.gatherInput("New user's password: ");
//        String phoneNumber = io.gatherInput("New user's phone number: ");
//        int idRole = io.gatherIntInput("New user's role id(1 - Admin " +
//                "2 - Mentor 3 - Student 4 - Employee): ", 1, 4);
//        return new User(name, surname, email, password, phoneNumber, idRole);
//    }

//    public void addUserToDB() {
//        User newUser = createUser();
//        addUser(newUser);
//    }

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
