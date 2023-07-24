package com.example.vision_app.Model;

import java.io.Serializable;

public class Recharge implements Serializable {
    private int id_recharge,status_recharge;
    private byte[] recceipt_image;
    private String user_name,date_recharge,describe_recharge;
    private double rechare_amount;


    public Recharge(int id_recharge,String user_name,String date_recharge,double rechare_amount,byte[] recceipt_image,int status_recharge) {
        this.id_recharge = id_recharge;
        this.recceipt_image = recceipt_image;
        this.user_name = user_name;
        this.date_recharge = date_recharge;
        this.rechare_amount = rechare_amount;
    }

    public Recharge() {
    }

    public String getDescribe_recharge() {
        return describe_recharge;
    }

    public void setDescribe_recharge(String describe_recharge) {
        this.describe_recharge = describe_recharge;
    }

    public byte[] getRecceipt_image() {
        return recceipt_image;
    }

    public void setRecceipt_image(byte[] recceipt_image) {
        this.recceipt_image = recceipt_image;
    }

    public int getStatus_recharge() {
        return status_recharge;
    }

    public void setStatus_recharge(int status_recharge) {
        this.status_recharge = status_recharge;
    }

    public int getId_recharge() {
        return id_recharge;
    }

    public void setId_recharge(int id_recharge) {
        this.id_recharge = id_recharge;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDate_recharge() {
        return date_recharge;
    }

    public void setDate_recharge(String date_recharge) {
        this.date_recharge = date_recharge;
    }

    public double getRechare_amount() {
        return rechare_amount;
    }

    public void setRechare_amount(double rechare_amount) {
        this.rechare_amount = rechare_amount;
    }
}
