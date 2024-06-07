package com.example.project;

public class Pizza {
    private int ID;
    private String name;
    private Double price;
    private String imageUrl;

    private int quantity;
    private String category;


    public Pizza( ) {

    }
    public Pizza(String name) {
        this.name = name;
    }


    public Pizza(int pizzaID, String pizzaName, double pizzaPrice, String pizzaURL, String pizzaCategory) {
    this.name=pizzaName;
    this.price=pizzaPrice;
    this.imageUrl=pizzaURL;
    this.category=pizzaCategory;
    this.ID= pizzaID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }




    public Pizza(int id, String name, Double price, int quantity) {
        ID = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    @Override
    public String toString() {
        return "Pizza{" +
                "\nID= " + ID +
                "\nname= " + name +
                "\nprice= " + price +
                "\nquantity= " + quantity +
                +'\n'+'}'+'\n';
    }


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
