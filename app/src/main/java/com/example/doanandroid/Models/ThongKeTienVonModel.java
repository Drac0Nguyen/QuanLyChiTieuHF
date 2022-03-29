package com.example.doanandroid.Models;

public class ThongKeTienVonModel {
    private String thang,thunhap,chitieu,sodu;

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getThunhap() {
        return thunhap;
    }

    public void setThunhap(String thunhap) {
        this.thunhap = thunhap;
    }

    public String getChitieu() {
        return chitieu;
    }

    public void setChitieu(String chitieu) {
        this.chitieu = chitieu;
    }

    public String getSodu() {
        return sodu;
    }

    public void setSodu(String sodu) {
        this.sodu = sodu;
    }

    public ThongKeTienVonModel(String thang, String thunhap, String chitieu, String sodu) {
        this.thang = thang;
        this.thunhap = thunhap;
        this.chitieu = chitieu;
        this.sodu = sodu;
    }

    public ThongKeTienVonModel(String thang, String thunhap, String chitieu) {
        this.thang = thang;
        this.thunhap = thunhap;
        this.chitieu = chitieu;
    }
}
