package com.example.project;

public class User {
    private String name;
    private String last_name;
    private long id;
    private String phone;
    private String email;
    private String password;
    private String gender;
    private String profilePicturePath; // Add this field


    public User(String name, String last_name,  long id, String phone, String gender, String email, String password) {
        this.name = name;
        this.last_name=last_name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.gender=gender;
    }

    public User(String name,  String last_name,String phone, String gender, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.last_name=last_name;
        this.email = email;
        this.password = password;
        this.gender=gender;


    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getGender() {
        return gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
}
