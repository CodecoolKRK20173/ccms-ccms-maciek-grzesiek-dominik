package com.codecool.controllers;

import com.codecool.models.Classes;

import java.util.HashMap;
import java.util.Map;

public class MentorController extends Controller {


    @Override
    void initializeActions() {
        this.actionMenu = new HashMap<>();
        this.actionMenu.put(1, userDao::printStudentsListAsMentor);
        this.actionMenu.put(2, this::addAssignment);
        this.actionMenu.put(3, this::gradeAssignment);
//        this.actionMenu.put(4, user::checkAttendance);
//        this.actionMenu.put(5, user::addStudentToClass);
//        this.actionMenu.put(6, user::removeStudentFromClass);
        this.actionMenu.put(4, this::editStudentData);
        this.actionMenu.put(5, this::logout);
    }

    @Override
    void initializeChoiceMenu() {
        this.choiceMap = new HashMap<>();
        this.choiceMap.put(1, "Show students list");
        this.choiceMap.put(2, "Add an assignment");
        this.choiceMap.put(3, "Grade an assignment");
        this.choiceMap.put(4, "Check students attendance");
        this.choiceMap.put(5, "Add student to class");
        this.choiceMap.put(6, "Remove student from class");
        this.choiceMap.put(7, "Edit student's data");
        this.choiceMap.put(8, "Logout");
    }

    private void addAssignment() {
        System.out.println("You are adding new assignment to data base");
        String name = io.gatherInput("Enter name of new assignment: ");
        mentorDao.addAssignment(new Classes(0, name));
    }

    private void gradeAssignment() {
        System.out.println("You are changing student's grade");
        gradesDao.showAllGradesByUser();
        int assignmentId = io.gatherIntInput("Enter assignment ID to grade it",1,666); // TODO@ POPRAWIC max range
        String grade = io.gatherInput("Is assignment passed?:" );
        mentorDao.gradeAssignment(assignmentId, grade);
    }

    private void editStudentData() {
        System.out.println("You are changing student's data");
        userDao.printStudentsListAsMentor();
        int studentId = io.gatherIntInput("Choose student's ID to edit", 1, 666); // TODO max range
        String newName = io.gatherInput("Enter new student's name: ");
        String newSurname = io.gatherInput("Enter new student's surname: ");
        String newEmail = io.gatherInput("Enter new student's email: ");
        String newPhoneNumber = io.gatherInput("Enter new student's phone number: ");
        mentorDao.editStudentData(studentId, newName, newSurname, newEmail, newPhoneNumber);
    }
}
