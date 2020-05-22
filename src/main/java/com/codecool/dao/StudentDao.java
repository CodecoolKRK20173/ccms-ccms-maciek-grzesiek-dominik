package com.codecool.dao;

import com.jakewharton.fliptables.FlipTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentDao extends Dao {

    public void submitAssignment(int studentID, String assignmentName, String filePath) {
        connect();
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        try {
            statement.executeUpdate("UPDATE UserAssignment " +
                    "SET SubmitedAt = " + date + ", " +
                    "FilePath = " + filePath + " " +
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

    public void printStudentGrades(int studentID) {

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
                    "WHERE u.ID = " + studentID + ";");
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
        printGradesList(grades);
    }

    private void printGradesList(List<List<String>> gradesList) {
        if (gradesList.size() == 0) return;
        String[] headers = {"Assignment_name", "Evaluator_name", "Evaluator_surname", "Date", "Grade"};
        int gradeSize = gradesList.get(0).size();
        String[][] grades = new String[gradesList.size()][gradeSize];
        for (int i = 0; i < grades.length; i++) {
            grades[i] = gradesList.get(0).toArray(new String[gradeSize]);
        }
        System.out.println(FlipTable.of(headers, grades));
    }
}
