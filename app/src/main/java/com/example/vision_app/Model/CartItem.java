package com.example.vision_app.Model;

public class CartItem {
    private int id_cart;
    private int productId;
    private String productName;
    private double productPrice;
    private byte[] productImage;
    private int quantity;

    public CartItem(int productId, String productName, double productPrice, byte[] productImage, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.quantity = quantity;
    }

    public CartItem(int id_cart, int productId, String productName, double productPrice, byte[] productImage, int quantity) {
        this.id_cart = id_cart;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
        this.quantity = quantity;
    }

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }

    public CartItem() {

    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
