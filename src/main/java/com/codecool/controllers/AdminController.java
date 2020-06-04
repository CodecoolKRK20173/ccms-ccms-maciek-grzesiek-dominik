package com.codecool.controllers;

import com.codecool.models.User;

import java.util.HashMap;

public class AdminController extends Controller {

    @Override
    void initializeChoiceMenu() {
        this.choiceMap = new HashMap<>();
        this.choiceMap.put(1, "Add new mentor");
        this.choiceMap.put(2, "Remove mentor");
        this.choiceMap.put(3, "Edit mentor's data");
        this.choiceMap.put(4, "Show mentors list");
        this.choiceMap.put(5, "Show students list");
        this.choiceMap.put(6, "Logout");
    }

    @Override
    void initializeActions() {
        this.actionMenu = new HashMap<>();
        this.actionMenu.put(1, this::addMentorToDB);
        this.actionMenu.put(2, this::removeMentor);
        this.actionMenu.put(3, this::editMentor);
        this.actionMenu.put(4, userDao::printMentorsList);
        this.actionMenu.put(5, userDao::printStudentsListAsAdmin);
        this.actionMenu.put(6, this::logout);
    }

    private void addMentorToDB () {
        User newUser = createMentor();
        adminDao.addMentor(newUser);
    }

    private User createMentor () {
        String name = io.gatherInput("New mentor's name: ");
        String surname = io.gatherInput("New mentor's surname: ");
        String email = io.gatherInput("New mentor's email: ");
        String password = io.gatherInput("New mentor's password: ");
        String phoneNumber = io.gatherInput("New mentor's phone number: ");
        int idRole = 2;
        return new User(name, surname, email, password, phoneNumber, idRole);
    }

    private void removeMentor () {
        adminDao.remove("Users", io.gatherInput("Give id of mentor that you want to remove"));
    }

    private void editMentor() {
        adminDao.editMentorData(io.gatherInput("Give id of mentor whose data you want to edit: "),
                io.gatherInput("Give name of parameter that you want to change:"),
                io.gatherInput("Give new value of that data: "));
    }


}
