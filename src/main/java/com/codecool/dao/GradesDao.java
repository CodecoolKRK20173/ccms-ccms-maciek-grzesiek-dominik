package com.codecool.dao;

import com.codecool.models.Grades;
import com.jakewharton.fliptables.FlipTableConverters;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GradesDao extends Dao {
    public List<Grades> getUserAssignmentsAndGrades() {
        List<Grades> userAssignmentAndGradesList = new ArrayList<>();
        connect();
        try {
            ResultSet results = statement.executeQuery("SELECT * FROM Grades");
            while (results.next()) {
                userAssignmentAndGradesList.add(createGrades(results));
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userAssignmentAndGradesList;
    }

    public Grades createGrades(ResultSet results) throws SQLException {
        int userAssignmentId = results.getInt("UserAssignmentID");
        int evaluatorId = results.getInt("EvaluatorID");
        String grade = results.getString("Grade");
        String createdAt = results.getString("CreatedAt");
        return new Grades(userAssignmentId,evaluatorId,grade,createdAt);
    }

    public void showAllGradesByUser() {
        String sql = "SELECT ua.Id, ua.Assignment, ua.UserId, ua.CreatedAt, ua.SubmitedAt, \n" +
                "u.Name, u.Surname, g.Grade, g.CreatedAt, g.EvaluatorId\n" +
                "FROM UserAssignment ua\n" +
                "JOIN Users u ON u.UserId = ua.UserId\n" +
                "JOIN Grades g ON g.UserAssignmentID = ua.Id;";
        connect();
        try {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println(FlipTableConverters.fromResultSet(rs));
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
