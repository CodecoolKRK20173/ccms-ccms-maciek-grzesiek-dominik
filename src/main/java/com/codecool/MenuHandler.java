package com.codecool;

import com.codecool.dao.*;
import com.codecool.models.User;

import javax.print.attribute.HashAttributeSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuHandler {
    public boolean isRunning;
    private Map<Integer, Runnable> mainMEmployeeDao;
    private String[] mainMenuList;
    private final UI ui;
    private final IO io;
    private AdminDao adminDao;
    private EmployeeDao employeeDao;
    private MentorDao mentorDao;
    private StudentDao studentDao;
    private UserDao userDao;
    private Map<Integer, Runnable> adminMenu;
    private Map<Integer, Runnable> employeeMenu;
    private Map<Integer, Runnable> mentorMenu;
    private Map<Integer, Runnable> studentMenu;
    private boolean isLogin;

    public MenuHandler() {
        this.isRunning = true;
        this.ui = new UI();
        this.io = new IO();
        initializeDao();
        initializeMainMenu();
    }

    private void initializeDao(){
        this.adminDao = new AdminDao();
        this.mentorDao = new MentorDao();
        this.studentDao = new StudentDao();
        this.userDao = new UserDao();
    }

    private void initializeMainMenu() {
        mainMenuList = new String[] {"1. Login", "2. Exit"};
        mainMenu = new HashMap<>();
        mainMenu.put(1, this::login);
        mainMenu.put(2, this::exit);
    }

    public void mainMenu() {
        ui.displayMainMenu();
        ui.displayInLine(mainMenuList);
        int userChoice = io.gatherIntInput("\nEnter a number: ",1, 2);
        mainMenu.get(userChoice).run();
    }

    private void login() {
        ui.clearScreen();
        String email = io.gatherInput("Enter Email: ");
        String password = io.gatherInput("Enter Password: ");
        User user = userDao.getUser(email, password);
        isLogin = true;
        switch (user.getRole()) {
            case 1:
                initializeAdminMenu();
                adminPanel();
                break;
            case 2:
                initializeEmployeeMenu(user);
                employeePanel();
                break;
            case 3:
                initializeMentorMenu(user);
                mentorPanel();
                break;
            case 4:
                initializeStudentMenu(user);
                studentPanel();
        }
    }

    private void exit() {
        isRunning = false;
    }

    private void initializeAdminMenu() {
        adminMenu = new HashMap<>();
        adminMenu.put(1, this::addUserToDB);
        adminMenu.put(2, this::removeUser);
//        adminMenu.put(3, this::editMentorData);
//        adminMenu.put(4, this::getMentorsList);
//        adminMenu.put(5, this::getStudentsList);
        adminMenu.put(6, this::isLogin);
    }

    private void adminPanel() {
        while(isLogin){
            ui.displayAdminMenu();
            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 6);
            adminMenu.get(userChoice).run();
        }
    }

    private void initializeEmployeeMenu(User user) {
        employeeMenu = new HashMap<>();
//        employeeMenu.put(1, EmployeeDao::getStudentsDetailsList);
        employeeMenu.put(2, this::isLogin);
    }

    private void employeePanel() {
        while(isLogin) {
            ui.displayEmployeeMenu();
            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 2);
            employeeMenu.get(userChoice).run();
        }
    }

    private void initializeMentorMenu(User user) {
        mentorMenu = new HashMap<>();
//        mentorMenu.put(1, user::getStudentsList);
//        mentorMenu.put(2, user::addAssignment);
//        mentorMenu.put(3, user::gradeAssignment);
//        mentorMenu.put(4, user::checkAttendance);
//        mentorMenu.put(5, user::addStudentToClass);
//        mentorMenu.put(6, user::removeStudentFromClass);
//        mentorMenu.put(7, user::editStudentData);
        mentorMenu.put(8, this::isLogin);
    }

    private void mentorPanel() {
        while (isLogin) {
            ui.displayMentorMenu();
            int userChoice = io.gatherIntInput("\nEnter a number: ",1,8);
            mentorMenu.get(userChoice).run();
        }
    }

    private void initializeStudentMenu(User user) {
        studentMenu = new HashMap<>();
//        studentMenu.put(1, user::submitAssignment);
//        studentMenu.put(2, user::showGrades);
        studentMenu.put(3, this::isLogin);
    }

    private void studentPanel() {
        while (isLogin) {
            ui.displayStudentMenu();
            int userChoice = io.gatherIntInput("\nEnter a number:",1,3);
            studentMenu.get(userChoice).run();
        }
    }

    private void isLogin() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }

    private void addUserToDB() {
        User newUser = createUser();
        adminDao.addUser(newUser);
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

    private void removeUser() {
        adminDao.remove("Users", io.gatherInput("Give id of student that you want to remove"));
    }
}