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

import com.example.vision_app.DAO.ProductTypeDao;
import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Fragment.Admin.ManageProduct;
import com.example.vision_app.List_Product;
import com.example.vision_app.Model.ProductType;
import com.example.vision_app.R;
import com.example.vision_app.ReachargeDetail;

import java.util.ArrayList;
public class ProTypeAdapter extends RecyclerView.Adapter<ProTypeAdapter.ProTypeViewHolder> {
    private Context context;
    private ArrayList<ProductType> list;
    private ProductType productType;

    private ProductTypeDao typeDao;
    private UserDao userDao;


    public ProTypeAdapter(Context context, ArrayList<ProductType> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produc_type,parent,false);
        return new ProTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProTypeViewHolder holder, int position) {
        typeDao = new ProductTypeDao(context);
        productType = list.get(position);
        userDao = new UserDao(context);
        int _id = position;

        SharedPreferences preferences = context.getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName","");
        int role = userDao.getUserByUserName(userName).getRole();

        if (productType != null && productType.getImage_typePro() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productType.getImage_typePro(), 0, productType.getImage_typePro().length);
            holder.img_loai_sp.setImageBitmap(bitmap);
        }

        holder.tvName_loaisp.setText(productType.getName_typePro());

        if(role==0){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage("Bạn có chắc chắn muốn xóa loại sp này không ?"+productType.getId_type_product()+"");

                    builder.setTitle("Thông báo");

                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(typeDao.deleteProType(productType.getId_type_product())>0){
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }else {
                                Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, List_Product.class);
                Bundle b = new Bundle();
                b.putInt("extra_idProductType",list.get(_id).getId_type_product());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(list.size()!=0){
            return list.size();
        }
        return 0;
    }

    class ProTypeViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_loai_sp;
        private TextView tvName_loaisp;
        public ProTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            img_loai_sp = itemView.findViewById(R.id.img_loai_sp);
            tvName_loaisp = itemView.findViewById(R.id.tvName_loaisp);
        }
    }
}
