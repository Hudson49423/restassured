package com.octopusbeach.restassured.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by hudson on 6/14/15.
 */
public class Item implements Serializable{
    private String name;
    private Calendar completedDate;
    private int id;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getDate() {
        return completedDate;
    }

    public void setDate(Calendar date) {
        this.completedDate = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
