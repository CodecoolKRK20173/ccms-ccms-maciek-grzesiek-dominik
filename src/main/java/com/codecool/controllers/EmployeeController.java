package com.codecool.controllers;

import java.util.HashMap;
import java.util.Map;

public class EmployeeController extends Controller {

    @Override
    void initializeActions() {
        this.actionMenu = new HashMap<>();
        this.actionMenu.put(1, userDao::printStudentsListAsEmployee);
        this.actionMenu.put(2, this::logout);
    }

    @Override
    void initializeChoiceMenu() {
        Map<Integer, String> customerChoiceMap = new HashMap<>();
        customerChoiceMap.put(1, "Show detailed students list");
        customerChoiceMap.put(2, "Logout");
    }

}
