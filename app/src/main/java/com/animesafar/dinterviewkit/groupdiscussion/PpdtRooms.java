package com.animesafar.dinterviewkit.groupdiscussion;


import java.util.Date;

public class PpdtRooms {
    private String title;
    private String imageName;
    private Date date;
    private String id;
    private Boolean isOpen;

    public PpdtRooms(String title, String imageName, Date date, String id, Boolean isOpen) {
        this.title = title;
        this.imageName = imageName;
        this.date = date;
        this.id = id;
        this.isOpen = isOpen;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
