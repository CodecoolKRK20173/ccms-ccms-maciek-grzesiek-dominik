package com.codecool.models;

public class User {
    private int id;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String phoneNumber;
    private int role;
    private int classId;


 

    public User(int id, String email, String password, String name, String surname, String phoneNumber, int role, int classId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.classId = classId;
    }

    public User (String email, String password, String name, String surname, String phoneNumber, int role){
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public int getRole() {
        return role;
    }

    public void setRole(int role) { this.role = role; }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setSurname(String surname) { this.surname = surname; }


    public String getSurname() { return surname; }

    public void setClassId(int classId) { this.classId = classId; }

    public int getClassId() { return classId; }
}