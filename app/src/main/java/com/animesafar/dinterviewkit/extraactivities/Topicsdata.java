package com.animesafar.dinterviewkit.extraactivities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topicsdata {

    @Expose
    @SerializedName("_id")
private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("Code")
    private String code;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
