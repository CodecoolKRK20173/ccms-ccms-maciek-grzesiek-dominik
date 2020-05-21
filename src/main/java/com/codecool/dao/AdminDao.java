package com.codecool.dao;

import com.codecool.IO;
import com.codecool.UI;
import com.codecool.models.User;

import java.util.List;

public class AdminDao extends Dao {
    private final IO io;
    private final UI ui;

    public AdminDao() {
        io = new IO();
        ui = new UI(); //TODO: move that to somewhere else!!!
    }

    private User createUser() {
        String name = io.gatherInput("New user's name: ");
        String surname = io.gatherInput("New user's surname: ");
        String email = io.gatherInput("New user's email: ");
        String password = io.gatherInput("New user's password: ");
        String phoneNumber = io.gatherInput("New user's phone number: ");
        int idRole = io.gatherIntInput("New user's role id(1 - Admin " +
                "2 - Mentor 3 - Student 4 - Employee): ", 1, 4);
        return new User(name, surname, email, password, phoneNumber, idRole);
    }

    public void addUserToDB() {
        User newUser = createUser();
        addUser(newUser);
    }

    public void removeUser(){
        String[] tempUsers = new String[getUsersList().size()];
        ui.displayInLine(getUsersList().toArray(tempUsers));
        remove("Users",io.gatherInput("Give id of student that you want to remove"));
    }

}
