package com.codecool.dao;

import com.codecool.models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserDao extends Dao{

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        connect();
        try {
            ResultSet results = statement.executeQuery("SELECT * FROM Users;");
            while (results.next()) {
                users.add(createUser(results));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User createUser(ResultSet results) throws SQLException{
        int userId = results.getInt("Id");
        String email = results.getString("Email");
        String password = results.getString("Password");
        String name = results.getString("Name");
        String surname = results.getString("Surname");
        String phoneNumber = results.getString("PhoneNumber");
        int role = results.getInt("RoleId");
        return new User(userId, email,password, name, surname, phoneNumber, role);
    }

    public User getUser(String email, String password) {
        connect();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE email = ? and password = ?;");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            List<User> users = getUsers();
            int indexDifference = 1;
            int id = results.getInt("id") - indexDifference;
            results.close();
            statement.close();
            connection.close();
            return users.get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new NoSuchElementException("There isn't user with specified data in database");
    }

    public void addUser(User user) {
        connect();
        PreparedStatement addUser;
        String addString = "INSERT INTO Users (name, email, password, phone, role_id) VALUES (?, ?, ?, ?, ?)";
        try {
            addUser = connection.prepareStatement(addString);
            addUser.setString(1, user.getName());
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
}