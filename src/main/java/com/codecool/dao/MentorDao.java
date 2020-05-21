package com.codecool.dao;

import com.codecool.models.Classes;
import com.codecool.models.User;
import com.jakewharton.fliptables.FlipTableConverters;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

    public void getStudentsList() {
        String sql = "SELECT Name, Surname, Email, ClassName FROM Users INNER JOIN Classes on Users.ClassId=Classes.ClassId";
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

    public void gradeAssignment() {
    connect();
    PreparedStatement gradeAssignment;
    String sql = "SELECT * FROM Grades"
    }
}
