package com.example.productapp.models;

public class Order {
    private int maChiTiet;
    private Product product;
    private int soLuong;
    private int UserId;

    public void setMaChiTiet(int maChiTiet) {
        this.maChiTiet = maChiTiet;
    }

    public void setMaSP(Product product) {
        this.product = product;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public Order(int maChiTiet, Product product, int soLuong, int UserId) {
        this.maChiTiet = maChiTiet;
        this.UserId = UserId;
        this.product = product;
        this.soLuong = soLuong;
    }

    public int getMaChiTiet() {
        return maChiTiet;
    }


    public Product getProduct() {
        return product;
    }

    public int getSoLuong() {
        return soLuong;
    }
}
