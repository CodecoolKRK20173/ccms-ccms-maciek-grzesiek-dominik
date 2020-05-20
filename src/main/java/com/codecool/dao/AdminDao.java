package com.codecool.dao;

import com.codecool.IO;
import com.codecool.models.User;

public class AdminDao extends Dao {
    private IO io;

    AdminDao() {
        io = new IO();
    }

    private User createUser() {
        String name = io.gatherInput("New user's name: ");
        String surname = io.gatherInput("New user's surname: ");
        String email = io.gatherInput("New user's email: ");
        String password = io.gatherInput("New user's password: ");
        String phoneNumber = io.gatherInput("New user's phone number: ");
        int idRole = io.gatherIntInput("New user's role id: ", 1, 4);
        return new User(name, surname, email, password, phoneNumber, idRole);
    }

    public void addUserToDB() {
        User newUser = createUser();
        addUser(newUser);
    }

}
