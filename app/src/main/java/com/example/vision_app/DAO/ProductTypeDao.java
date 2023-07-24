package com.example.vision_app.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vision_app.Helper.DBhelper;
import com.example.vision_app.Model.ProductType;

import java.util.ArrayList;


public class ProductTypeDao {
    private SQLiteDatabase db;

    public ProductTypeDao(Context mContext) {
        DBhelper bhelper = new DBhelper(mContext);
        this.db = bhelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<ProductType> get(String sql, String...slectionArgs){
        /*
         * Lấy dữ liệu theo cu truy vấn (sql) hoặc tham số lựa chọn (selectionAgrs)
         * */
        ArrayList<ProductType> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,slectionArgs);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                ProductType obj = new ProductType();
                obj.setId_type_product(cursor.getInt(cursor.getColumnIndex("id_type_product")));
                obj.setName_typePro(cursor.getString(cursor.getColumnIndex("name_type_pro")));
                obj.setImage_typePro(cursor.getBlob(cursor.getColumnIndex("image_type_pro")));
                list.add(obj);
                Log.d("obj_productType",obj.toString());
            }while (cursor.moveToNext());
        }
        return list;
    }

    public long insertProType(ProductType productType) {
        /*
         * Thêm mới một ProductType vào cơ sở dữ liệu.
         * */
        ContentValues values = new ContentValues();
        values.put("name_type_pro", productType.getName_typePro());
        values.put("image_type_pro", productType.getImage_typePro());
        long rowId;
        return  rowId = db.insert("product_type", null, values);

    }

    public void updateProType(ProductType productType) {
        /*
         * Cập nhật thông tin của một ProductType trong cơ sở dữ liệu.
         * */
        ContentValues values = new ContentValues();
        values.put("name_type_pro", productType.getName_typePro());
        values.put("image_type_pro", productType.getImage_typePro());

        String whereClause = "name_type_pro = ?";
        String[] whereArgs = {productType.getName_typePro()};

        int rowsAffected = db.update("product_type", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Log.d("updateProType", "ProductType updated successfully");
        } else {
            Log.d("updateProType", "Failed to update ProductType");
        }
    }

    public int deleteProType(int id) {
        /*
         * Xóa một ProductType khỏi cơ sở dữ liệu.
         * */
       return db.delete("product_type","id_type_product=?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public ArrayList<ProductType> getAllProType() {
        /*
         * Lấy danh sách tất cả ProductType từ cơ sở dữ liệu.
         * */
        String sql = "SELECT * FROM product_type ";
        ArrayList<ProductType> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ProductType obj = new ProductType();
                obj.setId_type_product(cursor.getInt(cursor.getColumnIndex("id_type_product")));
                obj.setName_typePro(cursor.getString(cursor.getColumnIndex("name_type_pro")));
                obj.setImage_typePro(cursor.getBlob(cursor.getColumnIndex("image_type_pro")));
                list.add(obj);
                Log.d("obj_productType", obj.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }
}
