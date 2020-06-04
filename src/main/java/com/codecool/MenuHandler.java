package com.codecool;

import com.codecool.controllers.*;
import com.codecool.dao.UserDao;
import com.codecool.models.User;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class MenuHandler {
    private final UI ui;
    private final IO io;
    private final ControllerHandler controllerHandler;
    private final UserDao userDao;
    public boolean isRunning;
    private String[] mainMenuList;
    private Map<Integer, Runnable> mainMenu;
    private User user;

    public MenuHandler() {
        this.isRunning = true;
        this.ui = new UI();
        this.io = new IO();
        userDao = new UserDao();
        this.controllerHandler = new ControllerHandler();
        initializeMainMenu();
    }

    private void initializeMainMenu() {
        mainMenuList = new String[]{"1. Login", "2. Exit"};
        mainMenu = new HashMap<>();
        mainMenu.put(1, this::login);
        mainMenu.put(2, this::exit);
    }

    public void mainMenu() {
        ui.displayMainMenu();
        ui.displayInLine(mainMenuList);
        int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 2);
        mainMenu.get(userChoice).run();
    }

    private void login() {
        ui.clearScreen();
        checkIfLoginSuccessful();
        switch (user.getRole()) {
            case 1:
                this.controllerHandler.setController(new AdminController());
                break;
            case 4:
                this.controllerHandler.setController(new EmployeeController());
                break;
            case 2:
                this.controllerHandler.setController(new MentorController());
                break;
            case 3:
                this.controllerHandler.setController(new StudentController(this.user));
                break;
        }
        this.controllerHandler.makeAction();
    }

    private void checkIfLoginSuccessful() {
        boolean loginSuccessful = false;
        while (!loginSuccessful) {
            String email = io.gatherInput("Enter Email: ");
            String password = io.gatherPassword();
            try{
            this.user = userDao.getUser(email, password);
            loginSuccessful = true;
            }catch (NoSuchElementException e){
                io.print(e.getMessage());
            }
        }
    }

    private void exit() {
        isRunning = false;
    }

}
