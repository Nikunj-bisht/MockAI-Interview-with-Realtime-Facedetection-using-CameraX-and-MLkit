package com.animesafar.dinterviewkit.datapackage;

public class Griddata {

    private String title;
    private String url;

    public Griddata(String data , String url){
        this.title = data;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


    public String getTitle() {
        return title;
    }

}
