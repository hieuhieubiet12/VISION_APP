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

public class ManageWithdraw_Recharge extends Fragment {
    RecyclerView rcv,rcv_rut;
    ArrayList<Recharge> list;
    RechargeAdapter adapter;

    RechargeDao dao;
    ArrayList<Withdraw> listWith;
    WithdrawAdapter withdrawAdapter;
    WithdrawDAO withdrawDAO;
    UserDao userDao;
    private TextView tv_toolbar;
    private RelativeLayout toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage_recharge_withdraw, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName","");
        userDao = new UserDao(getContext());
        list = dao.getAll();
        rcv.setAdapter(adapter);
        listWith=withdrawDAO.getAllWith();
        rcv_rut.setAdapter(withdrawAdapter);
    }

    private void initView(View v) {
        rcv = v.findViewById(R.id.rcv_qldnt);
        dao = new RechargeDao(getContext());
        adapter = new RechargeAdapter(list,getContext());
        rcv_rut=v.findViewById(R.id.rcv_qldrt);
        withdrawDAO=new WithdrawDAO(getContext());
        withdrawAdapter=new WithdrawAdapter(listWith,getContext());
    }


    @Override
    public void onResume() {
        super.onResume();
        list = dao.getAll();
        adapter = new RechargeAdapter(list,getContext());
        rcv.setAdapter(adapter);
        listWith=withdrawDAO.getAllWith();
        withdrawAdapter=new WithdrawAdapter(listWith,getContext());
        rcv_rut.setAdapter(withdrawAdapter);
    }
}
