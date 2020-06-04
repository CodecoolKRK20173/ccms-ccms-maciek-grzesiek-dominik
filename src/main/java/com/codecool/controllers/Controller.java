package com.codecool.controllers;

import com.codecool.IO;
import com.codecool.UI;
import com.codecool.dao.*;

import java.util.Map;

public abstract class Controller implements Controllable {

    final UI ui;
    final IO io;
    AdminDao adminDao;
    MentorDao mentorDao;
    StudentDao studentDao;
    UserDao userDao;
    GradesDao gradesDao;
    Map<Integer, String> choiceMap;
    Map<Integer, Runnable> actionMenu;
    private boolean isLogin = true;

    Controller() {
        this.ui = new UI();
        this.io = new IO();
        initializeDao();
        initializeActions();
        initializeChoiceMenu();
    }

    private void initializeDao() {
        this.adminDao = new AdminDao();
        this.mentorDao = new MentorDao();
        this.studentDao = new StudentDao();
        this.userDao = new UserDao();
        this.gradesDao = new GradesDao();
    }

    abstract void initializeActions();
    abstract void initializeChoiceMenu();

    @Override
    public void makeAction() {
        while (isLogin) {
            ui.displayUserMenu(choiceMap);
            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, actionMenu.size());
            actionMenu.get(userChoice).run();
        }
    }

    void logout() {
        this.isLogin = false;
    }


}
