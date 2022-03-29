package com.example.doanandroid.Models;

import java.io.Serializable;

public class ThongTinChiTieuModel  implements Serializable {

    private  int iD;                // 0
    private String moTa;            // 1
    private float gia;              // 2
    private String ngayThuNhap;     // 3
    private  String thangThuNhap;   // 4
    private String tenTheLoai;     // 5
    private int chudeID;          //6
    private String tenChuDe;     //7
    private String red;         //8
    private String green;      //9
    private String blue;      // 10
    private byte[] hinh;     // 11

    public ThongTinChiTieuModel(byte[] hinh) {
        this.hinh = hinh;
    }

    public ThongTinChiTieuModel(int iD, String moTa, float gia, String ngayThuNhap, String thangThuNhap, String tenTheLoai, int chudeID, String tenChuDe, String red, String green, String blue, byte[] hinh) {
        this.iD = iD;
        this.moTa = moTa;
        this.gia = gia;
        this.ngayThuNhap = ngayThuNhap;
        this.thangThuNhap = thangThuNhap;
        this.tenTheLoai = tenTheLoai;
        this.chudeID = chudeID;
        this.tenChuDe = tenChuDe;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.hinh = hinh;
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public String getNgayThuNhap() {
        return ngayThuNhap;
    }

    public void setNgayThuNhap(String ngayThuNhap) {
        this.ngayThuNhap = ngayThuNhap;
    }

    public String getThangThuNhap() {
        return thangThuNhap;
    }

    public void setThangThuNhap(String thangThuNhap) {
        this.thangThuNhap = thangThuNhap;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public int getChudeID() {
        return chudeID;
    }

    public void setChudeID(int chudeID) {
        this.chudeID = chudeID;
    }

    public String getTenChuDe() {
        return tenChuDe;
    }

    public void setTenChuDe(String tenChuDe) {
        this.tenChuDe = tenChuDe;
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

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }
}
