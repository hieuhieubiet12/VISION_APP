package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.UserDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Withdraw;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.R;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.RutTien;

public class WithdrawAdapter extends RecyclerView.Adapter<WithdrawAdapter.WithdrawViewHolder> {
    private UserDao userDao;
    private ArrayList<Withdraw> list;
    private Context context;

    public WithdrawAdapter(ArrayList<Withdraw> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public WithdrawViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ruttien,parent,false);
        return new WithdrawViewHolder(view);
    }



    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull WithdrawViewHolder holder, int position) {
        Withdraw withdraw=list.get(position);
        userDao=new UserDao(context);
        SharedPreferences sharedPreferences=context.getSharedPreferences("remember",Context.MODE_PRIVATE);
        String userName =sharedPreferences.getString("userName","");
        int role =userDao.getUserByUserName(userName).getRole();
        int status = withdraw.getStatus_withdraw();
        switch (status){
            case 0:
                holder.statusRut.setText("Chờ xác nhận");
                break;
            case 1:
                holder.statusRut.setText("Thành công");
                holder.statusRut.setTextColor(R.color.success);
                break;
            case 2:
                holder.statusRut.setText("Hủy");
                break;
        }
             holder.timeRut.setText(withdraw.getDate_withdraw());
            if(role == 0){
                holder.motaRut.setText(withdraw.getDescribe_withdraw());
            }else {
                holder.motaRut.setText(userName);
            }
            holder.soTienRut.setText(String.valueOf(withdraw.getWithdraw_amount())+"VNĐ");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, RutTien.class);
                    Bundle b = new Bundle();
                    b.putInt("id_withdraw_extra",withdraw.getId_withdraw());
                    b.putInt("statusWith_extra",status);
                    b.putString("timeWith_extra", withdraw.getDate_withdraw());
                    b.putString("userName_extra", withdraw.getUser_name());
                    b.putInt("with_amount_extra",withdraw.getWithdraw_amount());
                    i.putExtras(b);
                    context.startActivity(i);
                }
            });
        }


    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }
    public class WithdrawViewHolder extends RecyclerView.ViewHolder{
        TextView motaRut,timeRut,soTienRut,statusRut;
        public WithdrawViewHolder(@NonNull View itemView) {
            super(itemView);
            motaRut = itemView.findViewById(R.id.tv_motadRut);
            timeRut = itemView.findViewById(R.id.tv_timedRut);
            soTienRut = itemView.findViewById(R.id.tv_tiendRut);
            statusRut = itemView.findViewById(R.id.tv_trangThaidRut);
        }
    }
}


