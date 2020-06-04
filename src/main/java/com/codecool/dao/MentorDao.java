package com.codecool.dao;

import com.codecool.models.Classes;
import com.codecool.models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MentorDao extends Dao {
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

    public User createUser(ResultSet results) throws SQLException {
        int id = results.getInt("Id");
        String email = results.getString("Email");
        String password = results.getString("Password");
        String name = results.getString("Name");
        String surname = results.getString("Surname");
        String phoneNumber = results.getString("PhoneNumber");
        int roleId = results.getInt("RoleId");
        int classID = results.getInt("ClassId");
        return new User(id, email, password, name, surname, phoneNumber, roleId, classID);
    }

    public void addAssignment(Classes classes) {
        connect();
        PreparedStatement addAssignment;
        String sql = "INSERT INTO Assignments (Name) VALUES (?)";
        try {
            addAssignment = connection.prepareStatement(sql);
            addAssignment.setString(1, classes.getClassName());
            addAssignment.executeUpdate();
            addAssignment.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void gradeAssignment(int assignmentID, String grade) {
    connect();
    PreparedStatement gradeAssignment;
    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    String sql = "UPDATE Grades SET Grade = ?, CreatedAt = ? WHERE UserAssignmentID = ?";
    try {
        gradeAssignment = connection.prepareStatement(sql);
        gradeAssignment.setString(1, grade);
        gradeAssignment.setString(2, date);
        gradeAssignment.setInt(3, assignmentID);
        gradeAssignment.executeUpdate();
        connection.close();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
        }
    }

    public void editStudentData(int studentId, String newName, String newSurname, String newEmail, String newPhoneNumber) {
        connect();
        PreparedStatement editStudentData;
        String sql = "UPDATE Users SET Name = ?, Surname = ?, Email = ?, PhoneNumber = ? WHERE UserId = ?";
        try {
           editStudentData = connection.prepareStatement(sql);
           editStudentData.setString(1, newName);
           editStudentData.setString(2, newSurname);
           editStudentData.setString(3, newEmail);
           editStudentData.setString(4, newPhoneNumber);
           editStudentData.setInt(5, studentId);
           editStudentData.executeUpdate();
           connection.close();
           statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
