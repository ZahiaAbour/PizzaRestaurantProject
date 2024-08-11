package com.example.project;

import java.util.Date;

public class Order {
    private int ID;
//    private String date;

    private String cust_email;
    private Pizza pizza;
    private String size= "small";
    private double price;
    private int quantity= 1;
    private Date orderDate;



    public Order(int id, String cust_email, Pizza pizza, String size, double price, int quantity) {
        this.ID = id;
        this.cust_email = cust_email;
        this.pizza = pizza;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public Order(String cust_email, Pizza newPizza, String size, double price, int quantity) {
//        this.ID = id;
        this.cust_email = cust_email;
        this.pizza = newPizza;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public Order(String cust_email, Pizza newPizza, String size, double price, int quantity, Date date) {
//        this.ID = id;
        this.cust_email = cust_email;
        this.pizza = newPizza;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
        this.orderDate=date;
    }

    public Order() {

    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
    public void setPrice(double price){
        this.price=price;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
