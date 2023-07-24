package com.example.vision_app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vision_app.DAO.ProductDao;
import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Model.Product;
import com.example.vision_app.ProductDetail;
import com.example.vision_app.R;

import java.util.ArrayList;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context mContext;
    private ArrayList<Product> list;
    private Product product;
    private UserDao userDao;
    private ProductDao dao;
    public ProductAdapter(Context mContext, ArrayList<Product> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_of_item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view_of_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        int id = position;
        product = list.get(position);
        userDao = new UserDao(mContext);
        dao = new ProductDao(mContext);
        SharedPreferences preferences = mContext.getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName","");
        int role = userDao.getUserByUserName(userName).getRole();

        if(product != null && product.getImage()!= null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length);
            holder.img_item_imgProduct.setImageBitmap(bitmap);
        }
        holder.tv_item_nameProduct.setText(product.getName());
        holder.tv_item_Price.setText(String.valueOf(product.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProductDetail.class);
                Bundle b = new Bundle();
                b.putInt("extra_idProduct",list.get(id).getId());
                i.putExtras(b);
                mContext.startActivity(i);
            }
        });

        if(role==0){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    builder.setMessage("Bạn có chắc chắn muốn xóa loại sp này không ?"+product.getId()+"");

                    builder.setTitle("Thông báo");

                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if((dao.deleteProduct(list.get(id).getId()))>0){
                                Toast.makeText(mContext, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }else {
                                Toast.makeText(mContext, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    builder.setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_item_imgProduct;
        private TextView tv_item_nameProduct,tv_item_Price;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item_imgProduct = itemView.findViewById(R.id.img_item_imgProduct);
            tv_item_nameProduct = itemView.findViewById(R.id.tv_item_nameProduct);
            tv_item_Price = itemView.findViewById(R.id.tv_item_Price);
        }
    }
}
