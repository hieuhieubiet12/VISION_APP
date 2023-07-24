package com.example.vision_app.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vision_app.Helper.DBhelper;
import com.example.vision_app.Model.Product;

import java.util.ArrayList;

public class ProductDao {
    private SQLiteDatabase db;

    public ProductDao(Context mContext) {
        DBhelper bhelper = new DBhelper(mContext);
        this.db = bhelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<Product> get(String sql, String...slectionArgs){
        /*
         * Lấy dữ liệu theo cu truy vấn (sql) hoặc tham số lựa chọn (selectionAgrs)
         * */
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,slectionArgs);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                Product obj = new Product();
                obj.setId(cursor.getInt(cursor.getColumnIndex("id_product")));
                obj.setIdType(cursor.getInt(cursor.getColumnIndex("id_typeProduct")));
                obj.setName(cursor.getString(cursor.getColumnIndex("name_product")));
                obj.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                obj.setImage(cursor.getBlob(cursor.getColumnIndex("imge_product")));
                obj.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity_product")));
                obj.setStatus(cursor.getInt(cursor.getColumnIndex("status_product")));
                obj.setDescription(cursor.getString(cursor.getColumnIndex("describe_product")));
                list.add(obj);
                Log.d("obj_product",obj.toString());
            }while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<Product> getAllProduct(){
        String sqlGetAll = "select * from product order by id_product desc";
        ArrayList<Product> list = get(sqlGetAll,null);
        return list;
    }
    public ArrayList<Product> getAllProductById(int id){
        String sqlGetAll = "select * from product where id_typeProduct=?";
        ArrayList<Product> list = get(sqlGetAll,new String[]{String.valueOf(id)});

        return list;
    }
    public int deleteProduct(int id) {
        /*
         * Xóa một Product khỏi cơ sở dữ liệu.
         * */
        return db.delete("product","id_product=?", new String[]{String.valueOf(id)});
    }
    public long insertPro(Product product){
        ContentValues values = new ContentValues();
        values.put("id_typeProduct",product.getIdType());
        values.put("name_product",product.getName());
        values.put("price",product.getPrice());
        values.put("imge_product",product.getImage());
        values.put("quantity_product",product.getQuantity());
        values.put("status_product",product.getStatus());
        values.put("describe_product",product.getDescription());
        long rowId;
        return rowId = db.insert("product",null,values);
    }
    public Product getProductByIdProduct(int id_product) {
        String getProbyId = "select * from product where id_product=?";
        ArrayList<Product> list = get(getProbyId, String.valueOf(id_product));
        return list.get(0);
    }
}
