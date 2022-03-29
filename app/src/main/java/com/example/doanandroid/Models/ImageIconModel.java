package com.example.doanandroid.Models;

import java.io.Serializable;

public class ImageIconModel implements Serializable {
    private int id;
    private String red;
    private String green;
    private String blue;
    private int img;

    public ImageIconModel(int id, String red, String green, String blue, int img) {
        this.id = id;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
