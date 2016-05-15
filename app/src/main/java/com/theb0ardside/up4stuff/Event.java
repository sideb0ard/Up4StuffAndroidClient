package com.theb0ardside.up4stuff;

/**
 * Created by sideboard on 5/14/16.
 */
public class Event {
    private int id;
    private String title;
    private String location;
    private String description;

    public Event() {
    }

    public Event(String title, String where, String description) {
        this.title = title;
        this.location = where;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

