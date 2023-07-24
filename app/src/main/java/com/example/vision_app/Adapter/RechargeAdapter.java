package com.example.vision_app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Model.Recharge;
import com.example.vision_app.R;
import com.example.vision_app.ReachargeDetail;

import java.util.ArrayList;

public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.RechargeViewHolder> {
    private UserDao userDao;
    private ArrayList<Recharge> list;
    private Context context;

    public RechargeAdapter(ArrayList<Recharge> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RechargeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vitien,parent,false);
        return new RechargeViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RechargeViewHolder holder, int position) {
        Recharge recharge = list.get(position);
        userDao = new UserDao(context);
        SharedPreferences sharedPreferences = context.getSharedPreferences("remember",Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName","");
        int role = userDao.getUserByUserName(userName).getRole();
        int status = recharge.getStatus_recharge();
        switch (status){
            case 0:
                holder.status.setText("Chờ xác nhận");
                break;
            case 1:
                holder.status.setText("Thành công");
                holder.status.setTextColor(R.color.success);
                break;
            case 2:
                holder.status.setText("Hủy");
                break;
        }
        holder.time.setText(recharge.getDate_recharge());
        if(role == 0){
            holder.mota.setText(recharge.getDescribe_recharge());
        }else {
            holder.mota.setText(userName);
        }
        holder.soTien.setText(String.valueOf(recharge.getRechare_amount())+"VNĐ");
        //gửi dữ liệu qua màn hình chi tiết Recharge
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ReachargeDetail.class);
                Bundle b = new Bundle();
                b.putInt("id_recharge_extra",recharge.getId_recharge());
                Log.d("hhhhhhhh", "onClick: "+recharge.getId_recharge());

                b.putInt("status_extra",status);
                b.putString("time_extra", recharge.getDate_recharge());
                b.putString("userName_extra", recharge.getUser_name());
                b.putDouble("recharge_amount_extra",recharge.getRechare_amount());
                i.putExtras(b);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list!= null){
            return list.size();
        }
        return 0;
    }



    public class RechargeViewHolder extends RecyclerView.ViewHolder{
        TextView mota,time,soTien,status;
        public RechargeViewHolder(@NonNull View itemView) {
            super(itemView);
            mota = itemView.findViewById(R.id.tv_motadnt);
            time = itemView.findViewById(R.id.tv_timednt);
            soTien = itemView.findViewById(R.id.tv_tiendnt);
            status = itemView.findViewById(R.id.tv_trangThaidnt);
        }
    }
}
