package com.codecool.models;

public class Grades {
    private int userAssignmentId;
    private int evaluatorId;
    private String grade;
    private String createdAt;

    public Grades(int userAssignmentId, int evaluatorId, String grade, String createdAt) {
        this.userAssignmentId = userAssignmentId;
        this.evaluatorId = evaluatorId;
        this.grade = grade;
        this.createdAt = createdAt;
    }

    public int getUserAssignmentId() { return userAssignmentId; }

    public void setUserAssignmentId(int userAssignmentId) { this.userAssignmentId = userAssignmentId; }

    public int getEvaluatorId() { return evaluatorId; }

    public void setEvaluatorId(int evaluatorId) { this.evaluatorId = evaluatorId; }

    public String getGrade() { return grade; }

    public void setGrade(String grade) { this.grade = grade; }

    public String getCreatedAt() { return createdAt; }

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
