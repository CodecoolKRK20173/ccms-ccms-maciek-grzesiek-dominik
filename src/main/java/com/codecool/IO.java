package com.codecool;

import java.util.Scanner;

public class IO {
    public Scanner scan;

    public IO() {
        scan = new Scanner(System.in);
        scan.useDelimiter(System.lineSeparator());
    }

    public int gatherIntInput(String title, int minRange, int maxRange) {
        System.out.println(title);
        String userInput;
        int userInt = 1;
        boolean validInput = false;
        while (!validInput) {
            userInput = scan.next();
            if (!userInput.equals("")) {
                if (userInput.matches("^[0-9]*$")) {
                    userInt = Integer.parseInt(userInput);
                    if (userInt >= minRange && userInt <= maxRange) {
                        validInput = true;
                    }
                }
            }
        }
        return userInt;
    }

//    DO WYWALENIA?
//    public boolean isNumber(String input) {
//        return !input.equals("") && input.matches("^[0-9]*$");
//    }
//
//    public void gatherEmptyInput(String message) {
//        System.out.println(message);
//        scan.next();
//    }

    public String gatherInput(String message) {
        System.out.println(message);
        boolean validInput = true;
        String userInput = "";
        do {
            if (!validInput) {
                System.out.println("Your input must contain at least one character. Enter again: ");
            }
            validInput = false;
            userInput = scan.next();
            if (!userInput.equals("")) {
                validInput = true;
            }
        } while (!validInput);
        return userInput;
    }
}