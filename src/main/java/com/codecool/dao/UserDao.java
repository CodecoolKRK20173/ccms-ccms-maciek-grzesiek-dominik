package com.codecool.dao;

import com.codecool.models.User;
import com.jakewharton.fliptables.FlipTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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


    public void printStudentsListAsEmployee() {
        printUserInfo(false,  "\"Student\"", "Name", "Surname", "Email", "Phone_number");
    }

    public void printStudentsListAsAdmin() {
        printUserInfo(true,  "\"Student\"", "Name", "Surname", "Class_name");
    }

    public void printStudentsListAsMentor() {
        printUserInfo(true,  "\"Student\"", "Name", "Surname", "Email",
                "Phone_number", "Class_name");
    }

    public void printMentorsList() {
        printUserInfo(false, "\"Mentor\"", "Name", "Surname", "Email", "Phone_number");
    }

    private void printUserInfo(Boolean withGrades, String role, String... columns) {
        connect();
        List<List<String>> allUsersInfo = new ArrayList<>();
        try {
            ResultSet results = statement.executeQuery("SELECT u.UserID AS User_id, u.email AS email, " +
                    "u.password AS Password, u.name AS Name, u.surname AS Surname, c.ClassName AS Class_name, " +
                    "u.phonenumber AS Phone_number " +
                    "FROM Users u " +
                    "JOIN Role r ON r.ID = u.roleID " +
                    "LEFT JOIN UserClasses uc ON uc.UserID =  u.UserID " +
                    "LEFT JOIN Classes c ON c.classID = uc.classID " +
                    "WHERE r.role = " + role + ";");
            while(results.next()) {
                String id = results.getString("User_Id");
                List<String> newUser = new ArrayList<>();
                newUser.add(id);
                for (String element : columns) {
                    newUser.add(results.getString(element));
                }
                allUsersInfo.add(newUser);
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (withGrades) {
            allUsersInfo.forEach(n -> addUserGrades(n.get(0), n));
        }

        List<String> headersList = new ArrayList<>();
        headersList.add("user_id");
        headersList.addAll(Arrays.asList(columns));
        if(withGrades){
            headersList.add("grade");
        }
        String[] headers = new String[headersList.size()];
        headersList.toArray(headers);
        System.out.println(FlipTable.of(headers, makeArrayFromList(allUsersInfo)));
    }

    private String[][] makeArrayFromList(List<List<String>> userData) {
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
            }
            row++;
            column = 0;
        }
        return users;
    }

    private void addUserGrades(String id, List<String> newUser) {
        connect();
        try {
            ResultSet grades = statement.executeQuery("SELECT Grade FROM Grades g " +
                    "JOIN UserAssignment ua ON ua.ID = g.UserAssignmentID " +
                    "WHERE ua.UserID = " + id + ";");
            newUser.add("No grade");
            while (grades.next()) {
                String grade = grades.getString("Grade");
                System.out.println(grade);
                newUser.set(newUser.size()-1, grade);
                if (grade.toLowerCase().trim().equals("not passed")) break;
            }
            grades.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
