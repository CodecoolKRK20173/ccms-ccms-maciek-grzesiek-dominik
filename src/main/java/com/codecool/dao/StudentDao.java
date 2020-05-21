package com.codecool.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentDao extends Dao {

    //        studentMenu.put(1, user::submitAssignment);
    //        studentMenu.put(2, user::showGrades);

    public StudentDao() {

    }

    public void submitAssignment(int studentID, String assignmentName, String filePath) {
        connect();
        Date date = new Date(System.currentTimeMillis());
        try {
            statement.executeUpdate("UPDATE UserAssignment " +
                    "SET SubmitedAt = " + date.toString() + " " +
                    "SET FilePath = " + filePath + " " +
                    "WHERE userID = " + studentID + " AND " +
                    "AssignmentID = (SELECT ID FROM Assignments " +
                    "WHERE Name = " + assignmentName + ";");
            connection.commit();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public String[][] getGradesArray(int studentID, String assignmentName) {

        connect();
        List<List<String>> grades = new ArrayList<>();
        try {
            ResultSet results = statement.executeQuery("SELECT a.name AS assignment_name," +
                    "evaluator.name AS evaluator_name, evaluator.surname AS evaluator_surname" +
                    "g.CreatedAt AS date, g.grade AS grade" +
                    "FROM Grades g " +
                    "JOIN UserAssignment ua ON ua.ID = g.UserAssignmentID " +
                    "JOIN Users u ON u.ID = ua.ID " +
                    "JOIN Users evaluator ON g.ID = evaluator.ID " +
                    "JOIN Assignments a ON a.ID = ua.AssignmentID" +
                    "WHERE u.ID = " + studentID + " AND a.name = " + assignmentName + ";");
            while (results.next()) {
                List<String> grade = new ArrayList<>();
                grade.add(results.getString("assignment_name"));
                grade.add(results.getString("evaluator_name"));
                grade.add(results.getString("evaluator_surname"));
                grade.add(results.getString("date"));
                grade.add(results.getString("grade"));
                grades.add(grade);
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return makeArrayFromList(grades);

    }

    public String[][] getStudentsInfoArray(Boolean withGrades, String... columns) {
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
