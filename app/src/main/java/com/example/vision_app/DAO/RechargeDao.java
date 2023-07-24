package com.example.vision_app.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vision_app.Helper.DBhelper;
import com.example.vision_app.Model.Recharge;

import java.util.ArrayList;
public class RechargeDao {
    SQLiteDatabase db;
    public RechargeDao(Context context) {
        DBhelper bhelper = new DBhelper(context);
        db = bhelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public ArrayList<Recharge> get(String sql, String...slectionArgs){
        /*
         * Lấy dữ liệu theo cu truy vấn (sql) hoặc tham số lựa chọn (selectionAgrs)
         * */
        ArrayList<Recharge> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,slectionArgs);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                Recharge obj = new Recharge();
                obj.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                obj.setDate_recharge(cursor.getString(cursor.getColumnIndex("date_recharge")));
                obj.setRechare_amount(cursor.getInt(cursor.getColumnIndex("recharge_amount")));
                obj.setRecceipt_image(cursor.getBlob(cursor.getColumnIndex("receipt_image")));
                obj.setStatus_recharge(cursor.getInt(cursor.getColumnIndex("status_recharge")));
                list.add(obj);
                Log.d("obj_recharge",obj.toString());
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean insertRecharge(Recharge obj){
        /*
        * Thêm 1 đơn nạp
        * 1. CẦN 1 HÀM KHỞI TẠO CÓ 5 THAM SỐ (user_name,date_recharge,recharge_amount,reciept_image,status_recharge)
        * */
        ContentValues values = new ContentValues();
        values.put("user_name",obj.getUser_name());
        values.put("date_recharge",obj.getDate_recharge());
        values.put("recharge_amount",obj.getRechare_amount());
        values.put("receipt_image",obj.getRecceipt_image());
        values.put("status_recharge",obj.getStatus_recharge());
        long checkInsert = db.insert("recharge",null,values);
        return checkInsert != -1;
    }
    public ArrayList<Recharge> getRechargeAll(String userName){
        /*
         * Lấy ra tất cả đơn nạp của user bằng tên đăng nhập.
         * */
        String sqlGetAll = "select * from recharge where user_name=?";
        return get(sqlGetAll,new String[]{userName});
    }
    @SuppressLint("Range")
    public ArrayList<Recharge> getByName(String name){

        String sqlGetAll = "select * from recharge WHERE user_name = ?";
        int batchSize = 100;// đặt kích thước nhỏ phù hợp
        ArrayList<Recharge> allRecharge = new ArrayList<>();

        Cursor cursor = db.rawQuery(sqlGetAll,new String[]{name});
        if(cursor != null && cursor.moveToFirst()){
            do{
                Recharge obj = new Recharge();
                obj.setId_recharge(cursor.getInt(cursor.getColumnIndex("id_recharge")));
                obj.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                obj.setDate_recharge(cursor.getString(cursor.getColumnIndex("date_recharge")));
                obj.setRechare_amount(cursor.getInt(cursor.getColumnIndex("recharge_amount")));
                obj.setRecceipt_image(cursor.getBlob(cursor.getColumnIndex("receipt_image")));
                obj.setStatus_recharge(cursor.getInt(cursor.getColumnIndex("status_recharge")));
                allRecharge.add(obj);
            }while (cursor.moveToNext() && cursor.getPosition() % batchSize != 0);
        }
        cursor.close();
        return allRecharge;
    }
    @SuppressLint("Range")
    public ArrayList<Recharge> getAll(){
        /*
         * Lấy ra tất cả đơn nạp cần xác nhận của admin
         * bằng tên đăng nhập.
         * */
        String sqlGetAll = "select * from recharge";
        int batchSize = 100;// đặt kích thước nhỏ phù hợp
        ArrayList<Recharge> allRecharge = new ArrayList<>();

        Cursor cursor = db.rawQuery(sqlGetAll,null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                Recharge obj = new Recharge();
                obj.setId_recharge(cursor.getInt(cursor.getColumnIndex("id_recharge")));
                obj.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                obj.setDate_recharge(cursor.getString(cursor.getColumnIndex("date_recharge")));
                obj.setRechare_amount(cursor.getInt(cursor.getColumnIndex("recharge_amount")));
                obj.setRecceipt_image(cursor.getBlob(cursor.getColumnIndex("receipt_image")));
                obj.setStatus_recharge(cursor.getInt(cursor.getColumnIndex("status_recharge")));
                allRecharge.add(obj);
            }while (cursor.moveToNext() && cursor.getPosition() % batchSize != 0);
        }
        cursor.close();
        return allRecharge;
    }
    public boolean updateRecharge(Recharge obj){
        /*
         * Cập nhật đơn nạp trạng thái bằng mã đơn nạp.
         * */
        ContentValues values = new ContentValues();
        values.put("user_name",obj.getUser_name());
        values.put("date_recharge",obj.getDate_recharge());
        values.put("recharge_amount",obj.getRechare_amount());
        values.put("receipt_image",obj.getRecceipt_image());
        values.put("status_recharge",obj.getStatus_recharge());
        int checkUpdate = db.update("recharge",values,"id_recharge=?",new String[]{String.valueOf(obj.getId_recharge())});
        return checkUpdate > 0;
    }

    public boolean deleteRecharge(int id_recharge){
        /*
         * Xóa đơn nạp trạng thái bằng mã đơn nạp.
         * */
        int checkDelete = db.delete("recharge","id_recharge=?", new String[]{String.valueOf(id_recharge)});
        return checkDelete > 0;
}

    public boolean updateStatusRecharge(int status,int id_recharge){
        /*
         * Cập nhật trạng thái bằng id đơn nạp
         * */
        ContentValues values = new ContentValues();
        values.put("status_recharge",status);
        int checkUpdateStatus = db.update("recharge",values,"id_recharge=?", new String[]{String.valueOf(id_recharge)});
        return checkUpdateStatus >0;
    }
    @SuppressLint("Range")
    public byte[] getImageById(int id) {
        /*
         * Lấy ra hình ảnh từ cột "receipt_image"
         * trong bảng recharge dựa trên ID.
         */
        String sqlGetImageById = "SELECT receipt_image FROM recharge WHERE id_recharge = ?";
        byte[] imageData = null;

        Cursor cursor = db.rawQuery(sqlGetImageById, new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            imageData = cursor.getBlob(cursor.getColumnIndex("receipt_image"));
        }
        cursor.close();
        return imageData;
    }

}
