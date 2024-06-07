package com.example.project;

public class Order {
    private int ID;
//    private String date;

    private int cust_id;
    private Pizza pizza;
    private String size= "small";
    private double price;
    private int quantity= 1;
//    private String details;


    public Order(int id, int cust_id, Pizza pizza, String size, double price, int quantity) {
        this.ID = id;
        this.cust_id = cust_id;
        this.pizza = pizza;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public Order(int custId, Pizza newPizza, String size, double price, int quantity) {
//        this.ID = id;
        this.cust_id = custId;
        this.pizza = newPizza;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public Order() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(String size, int quantity) {
        double default_price= this.pizza.getPrice();
//        String size= this.getSize();
        if (size.equals("Small")){
            this.price=quantity*default_price;
        }
        else if (size.equals("Medium")){
            this.price=quantity*(default_price+default_price*0.5);
        }
        else {
            this.price=quantity*(default_price*2);
        }

    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
