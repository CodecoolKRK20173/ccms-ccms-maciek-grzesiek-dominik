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

    public String[][] getUserInfoArray(Boolean withGrades, String... columns) {
        connect();
        List<List<String>> allUsersInfo = new ArrayList<>();
        try {
            ResultSet results = statement.executeQuery("SELECT * FROM Users u" +
                    "JOIN Role r ON u.ID = r.ID" +
                    "WHERE r.ID = 1;");
            while(results.next()) {
                String id = results.getString("id");
                List<String> newUser = new ArrayList<>();
                newUser.add(id);
                for (String element : columns) {
                    newUser.add(results.getString(element));
                }
                if (withGrades) {
                    addUserGrades(id, newUser);
                }
                allUsersInfo.add(newUser);
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return makeArrayFromList(allUsersInfo);
    }

    public String[][] makeArrayFromList(List<List<String>> userData) {
        if (userData.size() == 0) {
            return new String[][] {{""}};
        }
        String[][] users = new String[userData.size()][userData.get(0).size()];
        int row = 0;
        int column = 0;
        for (List<String> user : userData) {
            for (String element : user) {
                users[row][column] = element;
                column++;
                System.out.print(element + " ");
            }
            System.out.println();
            row++;
            column = 0;
        }
        return users;
    }

    private void addUserGrades(String id, List<String> newUser) throws SQLException {
        ResultSet grades = statement.executeQuery("SELECT Grade FROM Grades g " +
                "JOIN UserAssignment ua ON ua.ID = g.UserAssignmentID " +
                "JOIN Users u ON u.ID = ua.UserID " +
                "WHERE u.ID = " + id + ";");
        StringBuilder gradesBuilder = new StringBuilder();
        while (grades.next()) {
            gradesBuilder.append(grades.getString("Grade"));
            gradesBuilder.append(",");
        }
        if (gradesBuilder.length() > 0) {
            gradesBuilder.deleteCharAt(gradesBuilder.length() - 1);
        }
        newUser.add(gradesBuilder.toString());
    }


}
