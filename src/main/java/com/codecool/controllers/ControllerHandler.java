package com.codecool.controllers;

public class ControllerHandler {

    private Controllable controller;

    public void setController(Controllable controller) {
        this.controller = controller;
    }

    public void makeAction() {
        controller.makeAction();
    }

}
