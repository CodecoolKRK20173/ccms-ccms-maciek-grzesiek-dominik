package com.codecool.dao;

import com.codecool.models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class UserDao extends Dao{


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
        String userId = results.getString("id");
        String name = results.getString("name");
        String password = results.getString("password");
        String email = results.getString("email");
        String phoneNumber = results.getString("phone");
        int role = results.getInt("role_id");
        return new User(userId, name, password, email,  phoneNumber, role);
    }
    }
