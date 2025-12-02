package com.example.tripplanningapp;

public class TripTask {

    private String id;        
    private String title;     
    private String city;      
    private String date;      
    private String type;      
    private boolean isPaid;   
    private boolean needReminder; 
    private String note;

    public TripTask() {
    }

    public TripTask(String id, String title, String city, String date,
                    String type, boolean isPaid, boolean needReminder, String note) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.date = date;
        this.type = type;
        this.isPaid = isPaid;
        this.needReminder = needReminder;
        this.note = note;
    }

    // Getters & Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isNeedReminder() {
        return needReminder;
    }

    public void setNeedReminder(boolean needReminder) {
        this.needReminder = needReminder;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
