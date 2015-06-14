package com.octopusbeach.restassured.model;

import java.util.Calendar;

/**
 * Created by hudson on 6/14/15.
 */
public class Item {
    private String name;
    private Calendar completedDate;

    public Item(String name, Calendar calendar) {
        this.name = name;
        completedDate = calendar;
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
}
