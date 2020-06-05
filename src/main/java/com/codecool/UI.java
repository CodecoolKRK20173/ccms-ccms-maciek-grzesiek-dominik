package com.codecool;

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



}