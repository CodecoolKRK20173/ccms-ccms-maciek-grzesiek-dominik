package com.codecool.dao;

import com.codecool.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class StudentDao extends Dao {

    //        studentMenu.put(1, user::submitAssignment);
    //        studentMenu.put(2, user::showGrades);

    public StudentDao() {

    }

    public void submitAssignment() {

    }

    public void showGrades() {

    }

    public String[][] getStudentsInfoArray(Boolean withGrades, String... columns) {
        connect();
        List<List<String>> allUsersInfo = new LinkedList<>();
        try {
            ResultSet results = statement.executeQuery("SELECT * FROM Users u" +
                    "JOIN Role r ON u.ID = r.ID" +
                    "WHERE r.ID = 1;");
            int userIndex = 0;
            while(results.next()) {
                String id = results.getString("id");
                List<String> newUser = new LinkedList<>();
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

    private String[][] makeArrayFromList(List<List<String>> userData) {
        if (userData.size() == 0) {
            return null;
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
