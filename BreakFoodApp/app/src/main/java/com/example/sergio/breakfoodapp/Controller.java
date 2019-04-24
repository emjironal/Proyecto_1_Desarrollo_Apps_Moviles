package com.example.sergio.breakfoodapp;

public class Controller {

    private int userID;
    private String username, correo;

    private static final Controller instance = new Controller();

    public synchronized static Controller getInstance(){
        return instance;
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


    public String getCorreo(){return correo;}

    public void setCorreo(String correo){this.correo = correo;}


}
