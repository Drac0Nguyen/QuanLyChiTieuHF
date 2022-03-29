package com.example.doanandroid.Models;

import java.io.Serializable;

public class TypeModel implements Serializable {
    private  int ID;
    private String text;
    private String red;
    private String green;
    private String blue;
    private byte[] imgView;
    private int chuDe;

    public TypeModel(int ID, String text, String red, String green, String blue, byte[] imgView, int chuDe) {
        this.ID = ID;
        this.text = text;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.imgView = imgView;
        this.chuDe = chuDe;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public byte[] getImgView() {
        return imgView;
    }

    public void setImgView(byte[] imgView) {
        this.imgView = imgView;
    }

    public int getChuDe() {
        return chuDe;
    }

    public void setChuDe(int chuDe) {
        this.chuDe = chuDe;
    }
}
