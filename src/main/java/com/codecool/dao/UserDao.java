package com.codecool.dao;

import com.codecool.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserDao extends Dao {

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

    public User getUser(String email, String password) {
        connect();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE email = ? and password = ?;");
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            List<User> users = getUsers();
            int indexDifference = 1;
            int id = results.getInt("UserId") - indexDifference;
            results.close();
            statement.close();
            connection.close();
            return users.get(id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        throw new NoSuchElementException("There isn't user with specified data in database");
    }
}
