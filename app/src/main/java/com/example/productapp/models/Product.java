package com.example.productapp.models;

public class Product {
    private int maSP;
    private String tenSP;
    private Double gia;
    private String moTa;
    private String hinhAnh;
    private int maLoaiSP;

    public Product(int maSP, String tenSP, Double gia, String moTa, String hinhAnh,int maLoaiSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.moTa = moTa;
        this.hinhAnh = hinhAnh;
        this.maLoaiSP = maLoaiSP;
    }

    public int getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public Double getGia() {
        return gia;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public int getMaLoaiSP() {
        return maLoaiSP;
    }


}
