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
        System.out.println("WELCOME TO ONLINE SHOP!");
        System.out.println("MAIN MENU: ");
    }

    public void displayInLine(String [] strings){
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public void displayAdminMenu(){
        clearScreen();
        Map<Integer, String> adminMenu = makeAdminChoiceMap();
        System.out.println("Admin MENU: ");
        adminMenu.forEach((k,v) -> System.out.println(k+". "+v));
    }

    public void displayCustomerMenu(){
        clearScreen();
        Map<Integer, String> customerMenu = makeCustomerChoiceMap();
        System.out.println("Customer MENU: ");
        customerMenu.forEach((k,v) -> System.out.println(k+". "+v));
    }

    private Map<Integer, String> makeCustomerChoiceMap() {
        Map<Integer, String> customerChoiceMap = new HashMap<>();
        customerChoiceMap.put(1, "Show my basket");
        customerChoiceMap.put(2, "Add product to basket");
        customerChoiceMap.put(3, "Remove product from basket");
        customerChoiceMap.put(4, "Edit product's quantity");
        customerChoiceMap.put(5, "Place an order");
        customerChoiceMap.put(6, "Show my previous orders");
        customerChoiceMap.put(7, "Show all available products with rates");
        customerChoiceMap.put(8, "Show products by category");
//        customerChoiceMap.put(9, "Check availability of product");
//        customerChoiceMap.put(10, "Rate product");
//        customerChoiceMap.put(11, "Statistics of orders");
        customerChoiceMap.put(12, "Logout");
        return customerChoiceMap;
    }

    private Map<Integer, String> makeAdminChoiceMap() {
        Map<Integer, String> adminChoiceMap = new HashMap<>();
        adminChoiceMap.put(1, "Create new product");
        adminChoiceMap.put(2, "Edit product");
        adminChoiceMap.put(3, "Deactivate product");
        adminChoiceMap.put(4, "Create product category");
        adminChoiceMap.put(5, "Edit product category");
//        adminChoiceMap.put(6, "Check orders statuses");
//        adminChoiceMap.put(7, "Discount product");
//        adminChoiceMap.put(8, "Check statistics");
        adminChoiceMap.put(9, "Logout");
        return adminChoiceMap;
    }
}