package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Helper.DBhelper;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Recharge;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Withdraw;

public class WithdrawDAO {
    SQLiteDatabase db;

    public WithdrawDAO(Context context) {
        DBhelper dBhelper=new DBhelper(context);
        db=dBhelper.getWritableDatabase();
    }
    @SuppressLint("Range")
    public ArrayList<Withdraw> get(String sql, String...slectionArgs){
        /*
         * Lấy dữ liệu theo cu truy vấn (sql) hoặc tham số lựa chọn (selectionAgrs)
         * */
        ArrayList<Withdraw> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,slectionArgs);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                Withdraw obj=new Withdraw();
                obj.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                obj.setDate_withdraw(cursor.getString(cursor.getColumnIndex("date_withdraw")));
                obj.setWithdraw_amount(cursor.getInt(cursor.getColumnIndex("withdraw_amount")));
                obj.setStatus_withdraw(cursor.getInt(cursor.getColumnIndex("status_withdraw")));
                list.add(obj);
                Log.d("obj_withdraw",obj.toString());
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean insertWithdraw(Withdraw obj){
        /*
         * Thêm 1 đơn nạp
         * 1. CẦN 1 HÀM KHỞI TẠO CÓ 5 THAM SỐ (user_name,date_recharge,recharge_amount,reciept_image,status_recharge)
         * */
        ContentValues values = new ContentValues();
        values.put("user_name",obj.getUser_name());
        values.put("date_withdraw",obj.getDate_withdraw());
        values.put("withdraw_amount",obj.getWithdraw_amount());
        values.put("status_withdraw",obj.getStatus_withdraw());
        long checkInsert = db.insert("withdraw",null,values);
        return checkInsert != -1;
    }
    public ArrayList<Withdraw> getWithdrawAll(String userName){
        /*
         * Lấy ra tất cả đơn rút của user bằng tên đăng nhập.
         * */
        String sqlGetAll = "select * from withdraw where user_name=?";
        return get(sqlGetAll,new String[]{userName});
    }
    @SuppressLint("Range")
    public ArrayList<Withdraw> getByName(String name){

        String sqlGetAll = "select * from withdraw WHERE user_name = ?";
        int batchSize = 100;// đặt kích thước nhỏ phù hợp
        ArrayList<Withdraw> allWithdraw = new ArrayList<>();

        Cursor cursor = db.rawQuery(sqlGetAll,new String[]{name});
        if(cursor != null && cursor.moveToFirst()){
            do{
                Withdraw obj=new Withdraw();
                obj.setId_withdraw(cursor.getInt(cursor.getColumnIndex("id_withdraw")));
                obj.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                obj.setDate_withdraw(cursor.getString(cursor.getColumnIndex("date_withdraw")));
                obj.setWithdraw_amount(cursor.getInt(cursor.getColumnIndex("withdraw_amount")));
                obj.setStatus_withdraw(cursor.getInt(cursor.getColumnIndex("status_withdraw")));
                allWithdraw.add(obj);
            }while (cursor.moveToNext() && cursor.getPosition() % batchSize != 0);
        }
        cursor.close();
        return allWithdraw;
    }
    @SuppressLint("Range")
    public ArrayList<Withdraw> getAllWith(){
        /*
         * Lấy ra tất cả đơn nạp cần xác nhận của admin
         * bằng tên đăng nhập.
         * */
        String sqlGetAll = "select * from withdraw";
        int batchSize = 100;// đặt kích thước nhỏ phù hợp
        ArrayList<Withdraw> allWithdraw = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlGetAll,null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                Withdraw obj=new Withdraw();
                obj.setId_withdraw(cursor.getInt(cursor.getColumnIndex("id_withdraw")));
                obj.setUser_name(cursor.getString(cursor.getColumnIndex("user_name")));
                obj.setDate_withdraw(cursor.getString(cursor.getColumnIndex("date_withdraw")));
                obj.setWithdraw_amount(cursor.getInt(cursor.getColumnIndex("withdraw_amount")));
                obj.setStatus_withdraw(cursor.getInt(cursor.getColumnIndex("status_withdraw")));
                allWithdraw.add(obj);
            }while (cursor.moveToNext() && cursor.getPosition() % batchSize != 0);
        }
        cursor.close();
        return allWithdraw;
    }
    public boolean updateWithdraw(Withdraw obj){
        /*
         * Cập nhật đơn nạp trạng thái bằng mã đơn nạp.
         * */
        ContentValues values = new ContentValues();
        values.put("user_name",obj.getUser_name());
        values.put("date_withdraw",obj.getDate_withdraw());
        values.put("withdraw_amount",obj.getWithdraw_amount());
        values.put("status_withdraw",obj.getStatus_withdraw());
        int checkUpdate = db.update("withdraw",values,"id_withdraw=?",new String[]{String.valueOf(obj.getId_withdraw())});
        return checkUpdate > 0;
    }

    public boolean deleteWithdraw(int id_withdraw){
        /*
         * Xóa đơn nạp trạng thái bằng mã đơn nạp.
         * */
        int checkDelete = db.delete("withdraw","id_withdraw=?", new String[]{String.valueOf(id_withdraw)});
        return checkDelete > 0;
    }

    public boolean updateStatusWithdraw(int status,int id_withdraw){
        /*
         * Cập nhật trạng thái bằng id đơn nạp
         * */
        ContentValues values = new ContentValues();
        values.put("status_withdraw",status);
        int checkUpdateStatus = db.update("withdraw",values,"id_withdraw=?", new String[]{String.valueOf(id_withdraw)});
        return checkUpdateStatus >0;
    }
    @SuppressLint("Range")
    public double getCurrentBalance() {
        double balance = 0;
        Cursor cursor = db.rawQuery("SELECT money FROM user WHERE user_name = 'user_name_rut'", null);
        if (cursor.moveToFirst()) {
            balance = cursor.getDouble(cursor.getColumnIndex("money"));
        }
        cursor.close();
        return balance;
    }
    private void updateBalance(double newBalance) {
        ContentValues values = new ContentValues();
        values.put("money", newBalance);
        db.update("user", values, "user_name = 'user_name_rut'", null);
    }

}
