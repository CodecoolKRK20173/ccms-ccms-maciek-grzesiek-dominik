package com.codecool;

import java.util.HashMap;
import java.util.Map;

public class UI {

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void displayMainMenu(){
        clearScreen();
        System.out.println("WELCOME TO CODECOOL!");
        System.out.println("MAIN MENU: ");
    }

    public void displayInLine(String [] strings){
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public void displayUserMenu(Map<Integer, String> choiceMap) {
        System.out.println("MENU: ");
        choiceMap.forEach((k,v) -> System.out.println(k+". "+v));
    }

    public void displayAdminMenu(){
        clearScreen();
        Map<Integer, String> adminMenu = makeAdminChoiceMap();
        System.out.println("Admin MENU: ");
        adminMenu.forEach((k,v) -> System.out.println(k+". "+v));
    }

    public void displayEmployeeMenu(){
        clearScreen();
        Map<Integer, String> employeeMenu = makeEmployeeChoiceMap();
        System.out.println("Employee MENU: ");
        employeeMenu.forEach((k,v) -> System.out.println(k+". "+v));
    }

    public void displayMentorMenu(){
        clearScreen();
        Map<Integer, String> mentorMenu = makeMentorChoiceMap();
        System.out.println("Mentor MENU: ");
        mentorMenu.forEach((k,v) -> System.out.println(k+". "+v));
    }

    public void displayStudentMenu(){
        clearScreen();
        Map<Integer, String> studentMenu = makeStudentChoiceMap();
        System.out.println("Student MENU: ");
        studentMenu.forEach((k,v) -> System.out.println(k+". "+v));
    }

    private Map<Integer, String> makeStudentChoiceMap(){
        clearScreen();
        Map<Integer, String> studentChoiceMap = new HashMap<>();
        studentChoiceMap.put(1, "Submit an assignment");
        studentChoiceMap.put(2, "Show my grades");
        studentChoiceMap.put(3, "Logout");
        return studentChoiceMap;
    }

    private Map<Integer, String> makeMentorChoiceMap() {
        Map<Integer, String > mentorChoiceMap = new HashMap<>();
        mentorChoiceMap.put(1, "Show students list");
        mentorChoiceMap.put(2, "Add an assignment");
        mentorChoiceMap.put(3, "Grade an assignment");
        mentorChoiceMap.put(4, "Check students attendance");
        mentorChoiceMap.put(5, "Add student to class");
        mentorChoiceMap.put(6, "Remove student from class");
        mentorChoiceMap.put(7, "Edit student's data");
        mentorChoiceMap.put(8, "Logout");
        return mentorChoiceMap;
    }

    private Map<Integer, String> makeEmployeeChoiceMap() {
        Map<Integer, String> customerChoiceMap = new HashMap<>();
        customerChoiceMap.put(1, "Show detailed students list");
        customerChoiceMap.put(2, "Logout");
        return customerChoiceMap;
    }

    private Map<Integer, String> makeAdminChoiceMap() {
        Map<Integer, String> adminChoiceMap = new HashMap<>();
        adminChoiceMap.put(1, "Add new mentor");
        adminChoiceMap.put(2, "Remove mentor");
        adminChoiceMap.put(3, "Edit mentor's data");
        adminChoiceMap.put(4, "Show mentors list");
        adminChoiceMap.put(5, "Show students list");
        adminChoiceMap.put(6, "Logout");
        return adminChoiceMap;
    }


}