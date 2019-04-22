package com.example.sergio.breakfoodapp;

public class Controller {

    private int userID;
    private String username;

    private static Controller instance;

    public static Controller getInstance(){
        return instance != null ? instance : new Controller();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
