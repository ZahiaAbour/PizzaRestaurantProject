package com.example.project;

public class Offer {
    private int ID;
    private Pizza pizza;
    private String size= "small";
    private double discount=0;
    private int quantity= 1;
    private double price_after= 0;

//    private start_date;
//    private end_date;


    public Offer(int ID, Pizza pizza, double discount, int quantity, double price_after) {
        this.ID = ID;
        this.pizza = pizza;
        this.discount = discount;
        this.quantity = quantity;
        this.price_after=price_after;
    }

    public Offer() {

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public void setPrice(String size, int quantity) {
        double default_price= this.pizza.getPrice();

        double discount = (1 - this.discount) ;
//        String size= this.getSize();
        if (size.equals("Small")){
            this.price_after=quantity*default_price*discount;
        }
        else if (size.equals("Medium")){
            this.price_after=quantity*(default_price+default_price*0.5)*discount;
        }
        else {
            this.price_after=quantity*(default_price*2)*discount;
        }

    }
    public void setPrice(double Price) {

            this.price_after=Price;


    }

    public double getPrice_after() {
        return price_after;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
