package com.codecool.models;

import com.codecool.IO;
import com.codecool.dao.BasketDao;
import com.codecool.dao.OrdersDao;
import com.codecool.dao.ProductDao;
import com.jakewharton.fliptables.FlipTable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private int phoneNumber;
    private int role;

    public User(int id, String name, String password, String email, int phoneNumber, int role) throws SQLException {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}