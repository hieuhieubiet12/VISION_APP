package com.example.vision_app.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vision_app.Helper.DBhelper;
import com.example.vision_app.Model.CartItem;
import com.example.vision_app.Model.Product;

import java.util.ArrayList;

public class CartDao {
    private SQLiteDatabase database;
    private String userName;
    private DBhelper dbHelper;
    ArrayList<CartItem> cartItems;



    public CartDao(Context context,String userName) {
        this.userName = userName;
        dbHelper = new DBhelper(context);
        database = dbHelper.getWritableDatabase();
    }





    //    @SuppressLint("Range")
//    public ArrayList<Product> getCartItems() {
//        ArrayList<Product> cartItems = new ArrayList<>();
//        Cursor cursor = database.rawQuery("SELECT * FROM product WHERE in_cart = 1", null);
//        while (cursor.moveToNext()) {
//            Product product = new Product();
//            product.setId(cursor.getInt(cursor.getColumnIndex("id_product")));
//            product.setName(cursor.getString(cursor.getColumnIndex("name_product")));
//            product.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
//            product.setImage(cursor.getBlob(cursor.getColumnIndex("imge_product")));
//            cartItems.add(product);
//        }
//        cursor.close();
//        return cartItems;
//    }

    //    public void addToCart(int productId) {
    //        ContentValues values = new ContentValues();
    //        values.put("user_name", userName);
    //        values.put("id_product", productId);
    //        database.insert("cartProduct", null, values);
    //    }

//    public void removeFromCart(int productId) {
//        ContentValues values = new ContentValues();
//        values.put("in_cart", 0);
//        database.update("product", values, "id_product=?", new String[]{String.valueOf(productId)});
//    }

    @SuppressLint("Range")
    public ArrayList<CartItem> getCartItems() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        String[] columns = {"id_product", "name_product", "price", "imge_product", "quantity"};
        String selection = "user_name = ?";
        String[] selectionArgs = {userName};
        Cursor cursor = database.query("cartProduct", columns, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            CartItem cartItem = new CartItem();
            cartItem.setProductId(cursor.getInt(cursor.getColumnIndex("id_product")));
            cartItem.setProductName(cursor.getString(cursor.getColumnIndex("name_product")));
            cartItem.setProductPrice(cursor.getDouble(cursor.getColumnIndex("price")));
            cartItem.setProductImage(cursor.getBlob(cursor.getColumnIndex("imge_product")));
            cartItem.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
            cartItems.add(cartItem);
        }
        cursor.close();
        return cartItems;
    }
    public boolean isProductInCart(int productId) {
        String[] columns = {"id_product"};
        String selection = "user_name = ? AND id_product = ?";
        String[] selectionArgs = {userName, String.valueOf(productId)};
        Cursor cursor = database.query("cartProduct", columns, selection, selectionArgs, null, null, null);
        boolean isProductInCart = cursor.moveToFirst();
        cursor.close();
        return isProductInCart;
    }
    public void addToCart(int productId, String productName, double price, byte[] image, int quantity) {
        ContentValues values = new ContentValues();
        values.put("user_name", userName);
        values.put("id_product", productId);
        values.put("name_product", productName);
        values.put("price", price);
        values.put("imge_product", image);
        values.put("quantity", quantity);
        database.insert("cartProduct", null, values);
    }

    public void removeFromCart(int productId) {
        String selection = "user_name = ? AND id_product = ?";
        String[] selectionArgs = {userName, String.valueOf(productId)};
        database.delete("cartProduct", selection, selectionArgs);
        cartItems = getCartItems();
    }
    public void removeFromCartById(int cartId, String userName) {
        String selection = "id_cart = ? AND user_name = ?";
        String[] selectionArgs = {String.valueOf(cartId), userName};
        database.delete("cartProduct", selection, selectionArgs);

    }

    public void increaseCartItemQuantity(int productId) {
        ContentValues values = new ContentValues();
        values.put("quantity", getCartItemQuantity(productId) + 1);
        String whereClause = "user_name = ? AND id_product = ?";
        String[] whereArgs = {userName, String.valueOf(productId)};
        database.update("cartProduct", values, whereClause, whereArgs);
    }

    public void decreaseCartItemQuantity(int productId) {
        int currentQuantity = getCartItemQuantity(productId);
        if (currentQuantity > 1) {
            ContentValues values = new ContentValues();
            values.put("quantity", currentQuantity - 1);
            String whereClause = "user_name = ? AND id_product = ?";
            String[] whereArgs = {userName, String.valueOf(productId)};
            database.update("cartProduct", values, whereClause, whereArgs);
        }
    }

    @SuppressLint("Range")
    private int getCartItemQuantity(int productId) {
        String[] columns = {"quantity"};
        String selection = "user_name = ? AND id_product = ?";
        String[] selectionArgs = {userName, String.valueOf(productId)};
        Cursor cursor = database.query("cartProduct", columns, selection, selectionArgs, null, null, null);
        int quantity = 0;
        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
        }
        cursor.close();
        return quantity;
    }
}


