package com.codecool.controllers;

import com.codecool.models.User;
import java.util.HashMap;

public class StudentController extends Controller {

    private final User user;

    public StudentController(User user) {
        this.user = user;
    }

    @Override
    void initializeActions() {
        this.actionMenu = new HashMap<>();
        this.actionMenu.put(1, this::submitAssignmentByUser);
        this.actionMenu.put(2, this::printAssignmentByUser);
        this.actionMenu.put(3, this::logout);
    }

    @Override
    void initializeChoiceMenu() {
        this.choiceMap = new HashMap<>();
        this.choiceMap.put(1, "Submit an assignment");
        this.choiceMap.put(2, "Show my grades");
        this.choiceMap.put(3, "Logout");
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
