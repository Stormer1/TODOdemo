package com.example.activitytest.bean;

import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

public class Book extends LitePalSupport {

    private int id;
    private String username;
    private String name;
    private String date;
    private String time;
    private long num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }


}
