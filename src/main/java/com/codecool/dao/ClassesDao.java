package com.codecool.dao;

import com.codecool.models.Classes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassesDao extends Dao {

    public List<Classes> getAllClasses() throws SQLException {
        List<Classes> classesList = new ArrayList<>();
        connect();
        try {
            ResultSet results = statement.executeQuery("SELECT * FROM Classes");
            while (results.next()) {
                int classId = results.getInt("ClassId");
                String className = results.getString("ClassName");
                classesList.add(createClasses(results));
            }
            results.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classesList;
    }

    public Classes createClasses(ResultSet results) throws SQLException {
        int classId = results.getInt("ClassId");
        String className = results.getString("ClassName");
        return new Classes(classId, className);
    }
}
