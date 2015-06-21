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
    private int color;
    private boolean isRepeating;
    private boolean isReminding;
    private Calendar repeatTime;

    public Item(String name, int color) {
        this.name = name;
        this.color = color;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isRepeating() {
        return isRepeating;
    }

    public void setIsRepeating(boolean isRepeating) {
        this.isRepeating = isRepeating;
    }

    public boolean isReminding() {
        return isReminding;
    }

    public void setIsReminding(boolean isReminding) {
        this.isReminding = isReminding;
    }

    public Calendar getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(Calendar repeatTime) {
        this.repeatTime = repeatTime;
    }
}
