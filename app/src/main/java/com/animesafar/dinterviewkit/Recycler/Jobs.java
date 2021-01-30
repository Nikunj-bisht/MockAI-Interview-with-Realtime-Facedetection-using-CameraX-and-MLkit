package com.animesafar.dinterviewkit.Recycler;

import java.io.Serializable;

public class Jobs implements Serializable {

    private  String title;
    private String description;


    public Jobs(String title,String description){

        this.title = title;
        this.description = description;

    }

    public String getTitle(){
        return  this.title;
    }

    public String getDescription(){
        return  this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
