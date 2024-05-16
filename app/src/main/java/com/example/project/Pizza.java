package com.example.project;

public class Pizza {
    private int ID;
    private String name;
    private Double price;
    private String imageUrl;

    public Pizza( ) {

    }
    public Pizza(String name) {
        this.name = name;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private int quantity;

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
