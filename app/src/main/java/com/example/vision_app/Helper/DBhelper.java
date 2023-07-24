package com.example.vision_app.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CRUD_VISION";
    public static final int VERSION = 1;
    public DBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Bảng sản phẩm.
        String TB_Product="CREATE TABLE product (\n" +
                "  id_product INTEGER PRIMARY KEY autoincrement ,\n" +
                "  id_typeProduct INTEGER references product_type(id_type_product),\n" +
                "  name_product TEXT not null,\n" +
                "  price double not null,\n" +
                "  imge_product BLOB,\n" +
                "  quantity_product INTEGER ,\n" +
                "  status_product INTEGER default 0,\n" +
                "  in_car integer default 0,\n"+
                "  describe_product TEXT)";
        db.execSQL(TB_Product);

        //Bảng loại sản phẩm.
        String TB_Product_Type = "create table product_type (id_type_product integer primary key autoincrement, " +
                "name_type_pro text not null, " +
                "image_type_pro blob)";
        db.execSQL(TB_Product_Type);

        //Bảng người dùng (user)
        String TB_User = "create table user (user_name text primary key unique not null, " +
                "id_location integer references id_location(location)," +
                "name text not null, " +
                "password text not null," +
                "n_phone text," +
                "date text," +
                "avatar blob," +
                "address text," +
                "money double default 0.0," +
                "point integer," +
                "role integer default 1)";
        db.execSQL(TB_User);

        //Bảng địa chỉ để chọn.
        String TB_Location = "create table location (id_location integer primary key autoincrement ,district text not null, city text not null,  describe_location text not null)";
        db.execSQL(TB_Location);

        //Bảng đơn hàng.
        String TB_Orders = "create table orders (id_orders  integer primary key autoincrement, " +
                "user_name  text not null references user (user_name), " +
                "creation_time  date not null," +
                "quantity_order integer not null," +
                "address_order text," +
                "status_order integer default 0)";
        db.execSQL(TB_Orders);

        //Bảng đơn hàng chi tiết của user
        String TB_Order_detail = "create table order_detail (id_order_detail integer primary key autoincrement, " +
                "id_cart integer not null references cartProduct(id_cart), " +
                "id_orders  integer not null references orders(id_orders)," +
                "id_voucher integer not null references voucher(id_voucher)," +
                "id_voucher_user integer not null references voucher_user(id_voucher_user)," +
                "quanity_order_detail integer not null," +
                "payment_method text)";
        db.execSQL(TB_Order_detail);

        //Bảng voucher chung
        String TB_Voucher = "create table voucher (id_voucher integer primary key autoincrement," +
                "about_voucher text not null," +
                "dis_money integer not null," +
                "status_voucher integer not null)";
        db.execSQL(TB_Voucher);

        //Bảng voucher của user
        String TB_Voucher_user ="create table voucher_user (id_voucher_user integer primary key autoincrement," +
                "id_voucher integer not null references voucher(id_voucher)," +
                "user_name text not null references user(user_name)," +
                "dis_money integer not null," +
                "status_voucher_user integer default 0)";
        db.execSQL(TB_Voucher_user);

        //Bảng đơn nạp.
        String TB_Recharge = "create table recharge(id_recharge integer primary key autoincrement," +
                "user_name text not null references user(user_name)," +
                "date_recharge text not null," +
                "recharge_amount integer not null," +
                "receipt_image blod," +
                "status_recharge integer default 0)";
        db.execSQL(TB_Recharge);

        //Bảng đơn rút
        String TB_Withdraw = "create table withdraw(id_withdraw integer primary key autoincrement," +
                "user_name text not null references user(user_name)," +
                "date_withdraw text not null," +
                "withdraw_amount integer not null," +
                "status_withdraw integer default 0)";
        db.execSQL(TB_Withdraw);

        //Bảng Đánh giá
        String TB_Assess = "create table assess(id_assess integer primary key autoincrement," +
                "user_name text not null references user(user_name)," +
                "id_product integer not null references product(id_product)," +
                "id_orders integer not null references orders(id_orders)," +
                "comment text," +
                "point_assess integer)";
        db.execSQL(TB_Assess);
        //Bảng giỏ hàng.
        String TB_Cart_Pro = "create table cartProduct(id_cart integer primary key autoincrement, " +
                "id_product INTEGER references id_product(product))";
        db.execSQL(TB_Cart_Pro);

        String INSERT_ADMIN = ("insert into user(user_name,name,password,role) values ('admin','Admin','123456',0)");
        db.execSQL(INSERT_ADMIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists product");
        db.execSQL("Drop table if exists product_type");
        db.execSQL("Drop table if exists user");
        db.execSQL("Drop table if exists orders");
        db.execSQL("Drop table if exists order_detail");
        db.execSQL("Drop table if exists voucher");
        db.execSQL("Drop table if exists voucher_user");
        db.execSQL("Drop table if exists recharge");
        db.execSQL("Drop table if exists withdraw");
        db.execSQL("Drop table if exists assess");
    }

}
