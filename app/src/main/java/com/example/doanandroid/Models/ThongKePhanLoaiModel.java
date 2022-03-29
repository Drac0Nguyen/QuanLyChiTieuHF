package com.example.doanandroid.Models;

public class ThongKePhanLoaiModel {
    private  byte[] imgChuDe;
    private  String TenDanhMuc,PhanTramHienThi;
    private  float SoTien;
     private  int PhanTramProgressBar;
     private  String red, green, blue;


    public ThongKePhanLoaiModel(byte[] imgChuDe, String tenDanhMuc, String phanTramHienThi, float soTien, int phanTramProgressBar, String red, String green, String blue) {
        this.imgChuDe = imgChuDe;
        TenDanhMuc = tenDanhMuc;
        PhanTramHienThi = phanTramHienThi;
        SoTien = soTien;
        PhanTramProgressBar = phanTramProgressBar;
        this.red = red;
        this.green = green;
        this.blue = blue;
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

    public String getTenDanhMuc() {
        return TenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        TenDanhMuc = tenDanhMuc;
    }

    public String getPhanTramHienThi() {
        return PhanTramHienThi;
    }

    public void setPhanTramHienThi(String phanTramHienThi) {
        PhanTramHienThi = phanTramHienThi;
    }

    public float getSoTien() {
        return SoTien;
    }

    public void setSoTien(float soTien) {
        SoTien = soTien;
    }

    public byte[] getImgChuDe() {
        return imgChuDe;
    }

    public void setImgChuDe(byte[] imgChuDe) {
        this.imgChuDe = imgChuDe;
    }

    public int getPhanTramProgressBar() {
        return PhanTramProgressBar;
    }

    public void setPhanTramProgressBar(int phanTramProgressBar) {
        PhanTramProgressBar = phanTramProgressBar;
    }
}
