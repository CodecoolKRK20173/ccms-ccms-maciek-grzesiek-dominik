package com.codecool.controllers;

import com.codecool.IO;
import com.codecool.UI;
import com.codecool.dao.*;
import com.codecool.models.Classes;
import com.codecool.models.User;

import java.util.HashMap;
import java.util.Map;

public class MenuHandler {
    private final UI ui;
    private final IO io;
    public boolean isRunning;
    private String[] mainMenuList;
    private AdminDao adminDao;
    private MentorDao mentorDao;
    private StudentDao studentDao;
    private UserDao userDao;
    private GradesDao gradesDao;
    private Map<Integer, Runnable> adminMenu;
    private Map<Integer, Runnable> employeeMenu;
    private Map<Integer, Runnable> mentorMenu;
    private Map<Integer, Runnable> studentMenu;
    private Map<Integer, Runnable> mainMenu;
    private User user;
    private boolean isLogin;

    public MenuHandler() {
        this.isRunning = true;
        this.ui = new UI();
        this.io = new IO();
        initializeDao();
        initializeMainMenu();
    }

    private void initializeDao() {
        this.adminDao = new AdminDao();
        this.mentorDao = new MentorDao();
        this.studentDao = new StudentDao();
        this.userDao = new UserDao();
        this.gradesDao = new GradesDao();
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
        String email = io.gatherInput("Enter Email: ");
        String password = io.gatherInput("Enter Password: ");
        this.user = userDao.getUser(email, password);
        isLogin = true;
        switch (user.getRole()) {
            case 1:
                initializeAdminMenu();
                adminPanel();
                break;
            case 4:
                initializeEmployeeMenu(user);
                employeePanel();
                break;
            case 2:
                initializeMentorMenu(user);
                mentorPanel();
                break;
            case 3:
                initializeStudentMenu(user);
                studentPanel();
        }
    }

    private void exit() {
        isRunning = false;
    }

    private void initializeAdminMenu() {
        adminMenu = new HashMap<>();
        adminMenu.put(1, this::addMentorToDB);
        adminMenu.put(2, this::removeMentor);
        adminMenu.put(3, this::editMentor);
        adminMenu.put(4, userDao::printMentorsList);
        adminMenu.put(5, userDao::printStudentsListAsAdmin);
        adminMenu.put(6, this::isLogin);
    }

    private void adminPanel() {
        while (isLogin) {
            ui.displayAdminMenu();
            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 6);
            adminMenu.get(userChoice).run();
        }
    }

    private void initializeEmployeeMenu(User user) {
        employeeMenu = new HashMap<>();
        employeeMenu.put(1, userDao::printStudentsListAsEmployee);
        employeeMenu.put(2, this::isLogin);
    }

    private void employeePanel() {
        while (isLogin) {
            ui.displayEmployeeMenu();
            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 2);
            employeeMenu.get(userChoice).run();
        }
    }

    private void initializeMentorMenu(User user) {
        mentorMenu = new HashMap<>();
        mentorMenu.put(1, userDao::printStudentsListAsMentor);
        mentorMenu.put(2, this::addAssignment);
        mentorMenu.put(3, this::gradeAssignment);
//        mentorMenu.put(4, user::checkAttendance);
//        mentorMenu.put(5, user::addStudentToClass);
//        mentorMenu.put(6, user::removeStudentFromClass);
//        mentorMenu.put(7, user::editStudentData);
        mentorMenu.put(8, this::isLogin);
    }

    private void mentorPanel() {
        while (isLogin) {
            ui.displayMentorMenu();
            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 8);
            mentorMenu.get(userChoice).run();
        }
    }

    private void initializeStudentMenu(User user) {
        studentMenu = new HashMap<>();
        studentMenu.put(1, this::submitAssignmentByUser);
        studentMenu.put(2, this::printAssignmentByUser);
        studentMenu.put(3, this::isLogin);
    }

    private void studentPanel() {
        while (isLogin) {
            ui.displayStudentMenu();
            int userChoice = io.gatherIntInput("\nEnter a number:", 1, 3);
            studentMenu.get(userChoice).run();
        }
    }

    private void isLogin() {
        this.user = null;
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }

    private void addAssignment() {
        System.out.println("You are adding new assignment to data base");
        String name = io.gatherInput("Enter name of new assignment: ");
        mentorDao.addAssignment(new Classes(0, name));
    }

    private void gradeAssignment() {
        System.out.println("You are changing student's grade");
        gradesDao.showAllGradesByUser();
        int assignmentId = io.gatherIntInput("Enter assignment ID to grade it",1,666); // POPRAWIC max range
        String grade = io.gatherInput("Is assignment passed?:" );
        mentorDao.gradeAssignment(assignmentId, grade);
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
  
    private void submitAssignmentByUser() {
        String assignmentName = io.gatherInput("Provide assignment's name");
        String filePath = io.gatherInput("Provide assignment's url");
        studentDao.submitAssignment(user.getId(), filePath, assignmentName);
    }

    private void printAssignmentByUser() {
        studentDao.printStudentGrades(this.user.getId());
    }
}
