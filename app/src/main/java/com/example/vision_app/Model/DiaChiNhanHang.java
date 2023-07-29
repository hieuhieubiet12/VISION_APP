package com.example.vision_app.Model;

public class DiaChiNhanHang  {
    private String name , num_phone , address , city , district , ward  ;

    public DiaChiNhanHang() {
    }

    public DiaChiNhanHang(String name, String num_phone, String address, String city, String district, String ward) {
        this.name = name;
        this.num_phone = num_phone;
        this.address = address;
        this.city = city;
        this.district = district;
        this.ward = ward;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum_phone() {
        return num_phone;
    }

    public void setNum_phone(String num_phone) {
        this.num_phone = num_phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }
}
