package com.example.vision_app.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vision_app.Helper.DBhelper;
import com.example.vision_app.Model.User;

import java.util.ArrayList;
public class UserDao {
    SQLiteDatabase db;

    public UserDao(Context context) {
        DBhelper bhelper = new DBhelper(context);
        this.db = bhelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public ArrayList<User> get(String sql, String...slectionArgs){
        /*
        * Lấy dữ liệu theo cu truy vấn (sql) hoặc tham số lựa chọn (selectionAgrs)
        * */
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,slectionArgs);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                User obj = new User();
                obj.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                obj.setName(cursor.getString(cursor.getColumnIndex("name")));
                obj.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                obj.setN_phone(cursor.getString(cursor.getColumnIndex("n_phone")));
                obj.setDate(cursor.getString(cursor.getColumnIndex("date")));
                obj.setAvatar(cursor.getBlob(cursor.getColumnIndex("avatar")));
                obj.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                obj.setMoney(cursor.getDouble(cursor.getColumnIndex("money")));
                obj.setPoint(cursor.getInt(cursor.getColumnIndex("point")));
                obj.setRole(cursor.getInt(cursor.getColumnIndex("role")));
                list.add(obj);
                Log.d("obj_user",obj.toString());
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean checkLogin(String user_name,String password) {
        /*
         * KIỂM TRA ĐĂNG NHẬP
         * NẾU TÊN ĐĂNG NHẬP (user_name) VÀ MẬT KHẨU (password) ĐÚNG THÌ list.size() != 0
         * --> ĐĂNG NHẬP THÀNH CÔNG
         * */
        String sqlCheckLogin = "SELECT * FROM user WHERE user_name=? AND password=?";
        ArrayList<User> list = get(sqlCheckLogin, user_name, password);
        return list.size() != 0;
    }
    public User getUserByUserName(String userName){
        /*
         * LẤY RA 1 ĐỐI TƯỢNG THEO (userName)
         * */
        String sqlGetByUserName = "SELECT * FROM user WHERE user_name=?";
        ArrayList<User> list = get(sqlGetByUserName, userName);
        return list.get(0);
    }
    public boolean insertUser(User object) {
        /*
         * THÊM MỚI 1 User
         * 1. CẦN 1 HÀM KHỞI TẠO CÓ 6 THAM SỐ
         * (use_name, name, password, n_phone,date,address);
         * */
        ContentValues values = new ContentValues();
        values.put("user_name", object.getUser_name());
        values.put("name", object.getName());
        values.put("password", object.getPassword());
        values.put("n_phone", object.getN_phone());
        values.put("date", object.getDate());
        values.put("address", object.getAddress());
        long checkInsert = db.insert("user", null, values);
        return checkInsert != -1;
    }

    public boolean updateMoney(String userName, double money) {
        /*
         * CẬP NHẬT GIÁ TRỊ MONEY CỦA USER
         * 1. CẦN TÊN NGƯỜI DÙNG (userName)
         * 2. GIÁ TRỊ MONEY MỚI (money)
         * */
        ContentValues values = new ContentValues();
        values.put("money", money);
        int rowsAffected = db.update("user", values, "user_name=?", new String[]{userName});
        return rowsAffected > 0;
    }

    @SuppressLint("Range")
    public double getSoDuTK(String userName){
        /*
         * Lấy giá trị money của người dùng dựa trên userName.
         * */
        double money = 0.0;

        String sql = "SELECT money FROM user WHERE user_name = ?";
        String[] selectionArgs = {userName};

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        if (cursor.moveToFirst()) {
            money = cursor.getDouble(cursor.getColumnIndex("money"));
        }
        cursor.close();

        return money;
    }
}
