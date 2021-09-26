package com.animesafar.dinterviewkit.extraactivities;

public class Message {

    private String message;
    private String user;
    Message(String mess , String user){
        this.message = mess;this.user = user;
    }

    public String getMessage(){
        return this.message;
    }

    public String getUser() {
        return user;
    }
}
