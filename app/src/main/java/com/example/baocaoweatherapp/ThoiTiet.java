package com.example.baocaoweatherapp;

public class ThoiTiet {
    public String ngay;
    public String status;
    public String image;
    public String maxTemp;
    public String minTemp;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public ThoiTiet() {
    }

    public ThoiTiet(String ngay, String status, String image, String maxTemp, String minTemp) {
        this.ngay = ngay;
        this.status = status;
        this.image = image;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }
}
