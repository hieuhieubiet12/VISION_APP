package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter.RechargeAdapter;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter.WithdrawAdapter;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.RechargeDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.UserDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.WithdrawDAO;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Recharge;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Withdraw;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.R;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.VitienActivity;

public class ManageWithdraw_Recharge extends Fragment {
   private RecyclerView rcv,rcv_rut;
    private ArrayList<Recharge> list;
    private RechargeAdapter adapter;

    private RechargeDao dao;
    private ArrayList<Withdraw> listWith;
    private WithdrawAdapter withdrawAdapter;
    private WithdrawDAO withdrawDAO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_recharge_withdraw, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        dao=new RechargeDao(getContext());
        rcv = view.findViewById(R.id.rcv_qldnt);
        rcv_rut=view.findViewById(R.id.rcv_qldrt);
        withdrawDAO=new WithdrawDAO(getContext());
        list=dao.getAll();
        adapter = new RechargeAdapter(list,getContext());
        rcv.setAdapter(adapter);
        listWith=withdrawDAO.getAllWith();
        withdrawAdapter=new WithdrawAdapter(listWith,getContext());
        rcv_rut.setAdapter(withdrawAdapter);
    }




    @Override
    public void onResume() {
        super.onResume();

        list=dao.getAll();
        adapter = new RechargeAdapter(list,getContext());
        rcv.setAdapter(adapter);
        listWith=withdrawDAO.getAllWith();
        withdrawAdapter=new WithdrawAdapter(listWith,getContext());
        rcv_rut.setAdapter(withdrawAdapter);

    }
}
