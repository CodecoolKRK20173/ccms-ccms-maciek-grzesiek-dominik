package com.codecool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuHandler {
    public boolean isRunning;
    private Map<Integer, Runnable> mainMenu;
    private String[] mainMenuList;
    private final UI ui;
    private final IO io;
    private UserDao userDao;
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private OrdersDao ordersDao;
    private Map<Integer, Runnable> adminMenu;
    private Map<Integer, Runnable> customerMenu;
    private boolean isLogin;

    public MenuHandler() {
        this.isRunning = true;
        this.ui = new UI();
        this.io = new IO();
        initializeDao();
        initializeMainMenu();
    }

    private void initializeDao(){
        this.userDao = new UserDao();
        this.productDao = new ProductDao();
        this.categoryDao = new CategoryDao();
        this.ordersDao = new OrdersDao();
    }

    private void initializeMainMenu() {
        mainMenuList = new String[] {"1. Create Account", "2. Login", "3. Exit"};
        mainMenu = new HashMap<>();
        mainMenu.put(1, this::createNewUser);
        mainMenu.put(2, this::login);
        mainMenu.put(3, this::exit);
    }

    public void mainMenu() {
        ui.displayMainMenu();
        ui.displayInLine(mainMenuList);
        int userChoice = io.gatherIntInput("\nEnter a number: ",1, 3);
        mainMenu.get(userChoice).run();
    }

    private void createNewUser() {
        String name = io.gatherInput("Enter your name: ");
        String email = io.gatherInput("Enter your email: ");
        //todo add double entering email and password for checking correctness and if is already in database
        String password = io.gatherInput("Enter your password: "); //todo cover password in console with "*"
        int phone = io.gatherIntInput("Enter your phone number: ",0, 999999999);
        int role = 2; //default for customer
        try {
            User user = new User(0, name, password, email, phone, role);
            userDao.addUser(user);
            io.gatherEmptyInput("Account successfully created!\nPress any key to back to main menu.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login() {
        ui.clearScreen();
        String email = io.gatherInput("Enter Email: ");
        String password = io.gatherInput("Enter Password: ");
        User user = userDao.getUser(email, password);
        isLogin = true;
        switch (user.getRole()) {
            case 1:
                initializeAdminMenu();
                adminPanel();
                break;
            case 2:
                initializeCustomerMenu(user);
                customerPanel();
                break;
        }
    }

    private void exit() {
        isRunning = false;
    }

    private void initializeAdminMenu() {
        adminMenu = new HashMap<>();
        adminMenu.put(1, this::addNewProductData);
        adminMenu.put(2, this::editProductData);
        adminMenu.put(3, this::deactivateProductData);
        adminMenu.put(4, this::getNewCategoryData);
        adminMenu.put(5, this::getCategoryData);
//        adminMenu.put(6, "Check orders statuses");
//        adminMenu.put(7, "Discount product");
//        adminMenu.put(8, "Check statistics");
        adminMenu.put(9, this::isLogin);
    }

    private void getCategoryData() {
        System.out.println("You are changing product category name");
        int id = io.gatherIntInput("Give category number to change: ",1,categoryDao.getCategories().size());
        String name = io.gatherInput("Give new name for category: ");
        categoryDao.editProductCategory(new Category(id, name));
    }

    private void getNewCategoryData() {
        System.out.println("You're adding new category to database");
        String newCategory = io.gatherInput("Enter name of new category: ");
        int isNewCatAvailable = io.gatherIntInput("Is new category available?: ",0,1);
        categoryDao.addNewCategory(new Category(isNewCatAvailable, newCategory));
    }

    private void showProductsByCategoryData() {
        System.out.println("Choose category: ");
        for (Category category: categoryDao.getCategories())
            System.out.println(category.getId() + " " + category.getName());
        int choice = io.gatherIntInput("Enter number of category: ",1, categoryDao.getCategories().size());
        productDao.showProductsByCategory(choice);
    }

    private void editProductData() {
        System.out.println("Editing product");
        productDao.showAllProducts();
        List<Product> products = productDao.getProducts();
        int id = io.gatherIntInput("Enter ID of product to change: ",1, productDao.getProducts().size())-1;
        Product product = products.get(id);
        String name = io.gatherInput("Enter new name of the product: ");
        product.setName(name);
        float price = io.gatherFloatInput("Enter new price of the product: ", (float) 0.01, 99999);
        product.setPrice(price);
        int amount = io.gatherIntInput("Enter new amount of the product: ", 0, 99999);
        product.setAmount(amount);
        productDao.editProduct(product);
    }

    private void addNewProductData() {
        System.out.println("You're adding new product to data base");
        String name = io.gatherInput("Enter name of new product: ");
        float price = io.gatherFloatInput("Enter new price of the product: ", (float) 0.01, (float) 99999);
        int amount = io.gatherIntInput("Enter new amount of the product: ",0, 99999);
        int isNewAvailable = io.gatherIntInput("Is new product available? ",0, 1);
        categoryDao.showAllCategories();
        int category = io.gatherIntInput("What is category of new product? ",1, 7);
        productDao.addNewProduct(new Product(0,name,price,amount,isNewAvailable,category));
    }

    private void adminPanel() {
        while(isLogin){
            ui.displayAdminMenu();
            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 9);
            adminMenu.get(userChoice).run();
        }
    }

    private void initializeCustomerMenu(User user) {
        customerMenu = new HashMap<>();
        customerMenu.put(1, user::seeAllProductsInBasket);
        customerMenu.put(2, user::addProductToBasket);
        customerMenu.put(3, user::removeProductFromBasket);
        customerMenu.put(4, user::editProductQuantity);
        customerMenu.put(5, user::placeOrder);
        customerMenu.put(6, user::showPreviousOrders);
        customerMenu.put(7, productDao::showProductsWithRates);
        customerMenu.put(8, this::showProductsByCategoryData);
//        customerMenu.put(9, "Check availability of product");
//        customerMenu.put(10, "Rate product");
//        customerMenu.put(11, "Statistics of orders");
        customerMenu.put(12, this::isLogin);
    }

    private void deactivateProductData() {
        productDao.showAllProducts();
        int choiceID = io.gatherIntInput("Enter ID of product: ", 1, productDao.getProducts().size());
        int choice = io.gatherIntInput("1 - activate product\n0 - deactivate product", 0, 1);
        productDao.deactivateProduct(choiceID, choice);
    }

    private void customerPanel() {
        while(isLogin) {
            ui.displayCustomerMenu();
            int userChoice = io.gatherIntInput("\nEnter a number: ", 1, 12);
            customerMenu.get(userChoice).run();
        }
    }

    private void isLogin() {
        isLogin = false;
        System.out.println("\nYou will be logged out\n");
    }
}