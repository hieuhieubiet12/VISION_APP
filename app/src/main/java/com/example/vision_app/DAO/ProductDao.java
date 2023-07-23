package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Helper.DBhelper;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Product;


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
    public Product getProductByProduct(int id_product) {
        String getProbyId = "select * from product where id=?";
        ArrayList<Product> list = get(getProbyId, String.valueOf(id_product));
        return list.get(0);
    }
}
