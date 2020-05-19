package com.codecool.models;

import com.codecool.IO;
import com.codecool.dao.BasketDao;
import com.codecool.dao.OrdersDao;
import com.codecool.dao.ProductDao;
import com.jakewharton.fliptables.FlipTable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private int phoneNumber;
    private int role;
    private Basket basket;
    private final IO io = new IO();

    public User(int id, String name, String password, String email, int phoneNumber, int role) throws SQLException {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        initializeNewBasket();
    }

    private void initializeNewBasket()  throws SQLException{
        this.basket = new Basket(0, new ArrayList<>());
        this.basket.setId(basket.getOrderID());
    }

    public void addProductToBasket(){
        System.out.println("You are adding product to basket");
        ProductDao productDao = new ProductDao();
        productDao.showAvailableProducts();
        List<Product> products = productDao.getProducts();
        int productID = io.gatherIntInput("Enter id of product:",1, 99999); //todo zmienic max range na dostepna w sklepie

        int indexDifference = 1;
        int id = productID - indexDifference;
        Product product = products.get(id);
        int amount = io.gatherIntInput("Enter amount of product: ",1,9999); //todo max range zmienic na max dostepna w sklepie
        this.getBasket().addProduct(product,amount);
        //added to test
        seeAllProductsInBasket();
    }

    public void removeProductFromBasket(){
        if (this.getBasket().getProducts().size() == 0 ) {
            System.out.println("Sorry your Basket is empty.");
            return;
        }
        System.out.println("You are removing product from basket");
        seeAllProductsInBasket();
        int productID = io.gatherIntInput("Enter id of product:",1, this.getBasket().getProducts().size());
        int indexDifference = 1;
        int id = productID - indexDifference;
        Product product = this.getBasket().getProducts().get(id);
        this.getBasket().deleteProduct(product);
    }

    public void editProductQuantity(){
        if (this.getBasket().getProducts().size() == 0 ) {
            System.out.println("Sorry your Basket is empty. Nothing to edit");
            return;
        }
        System.out.println("You are editing product quantity in basket");
        seeAllProductsInBasket();
        int productID = io.gatherIntInput("Enter id of product you want to change quantity:",1, this.getBasket().getProducts().size()); //
        int indexDifference = 1;
        int id = productID - indexDifference;
        Product product = this.getBasket().getProducts().get(id);
        int amount = io.gatherIntInput("Enter new amount of product: ",1,9999); //todo max range zmienic na max dostepna w sklepie
        this.getBasket().setProductQuantity(product, amount);
    }

    public void placeOrder() {
        try {
            OrdersDao ordersDao = new OrdersDao();
            ordersDao.addOrder(this);
            BasketDao basketDao = new BasketDao();
            basketDao.addBasketToBaskets(getBasket());
            ProductDao productDao = new ProductDao();
            productDao.updateProducts(getBasket());
            initializeNewBasket();
            System.out.println("Order placed successfully");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void seeAllProductsInBasket() {
        String[] innerHeaders = {"ID","Name", "Price", "Amount", "Total Price"};
        String[][] innerData = createInnerData();
        String inner = FlipTable.of(innerHeaders, innerData);
        String[] headers = getTotalExpenses();
        String[][] data = {{inner}};
        System.out.println(FlipTable.of(headers, data));
    }

    private String[][] createInnerData() {
        List<String[]> innerData = new ArrayList<>();
        if (getBasket().getProducts().size() != 0) {
            int i = 1;
            for (Product product : getBasket().getProducts()) {
                String[] temp = new String[5];
                temp[0] = String.valueOf(i++);//todo sprawdzic czy dobrze inkrementuje
                temp[1] = product.getName();
                temp[2] = String.valueOf(product.getPrice());
                temp[3] = String.valueOf(product.getAmount());
                temp[4] = String.valueOf(product.getPrice() * product.getAmount());
                innerData.add(temp);
            }
        } else {
            innerData.add(new String[]{"Sorry","Your", "Basket", "Is", "Empty"});
        }
        return convertTo2DArray(innerData);
    }

    private String[][] convertTo2DArray(List<String[]> innerData) {
        String[][] array2D = new String[innerData.size()][];
        for (int i = 0; i < array2D.length; i++) {
            String[] row = innerData.get(i);
            array2D[i] = row;
        }
        return array2D;
    }

    private String[] getTotalExpenses() {
        float totalExpenses = 0;
        if (getBasket().getProducts().size() != 0) {
            for (Product product : getBasket().getProducts()) {
                totalExpenses += (product.getPrice() * product.getAmount());
            }
        }
        return new String[]{"Total Expenses: " + totalExpenses};
    }


    public void showPreviousOrders() {
        OrdersDao ordersDao = new OrdersDao();
        System.out.println("Your Previous Orders List: ");
        ordersDao.showOrders(this);
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}