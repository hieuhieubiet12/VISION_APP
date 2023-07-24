package com.example.vision_app.Fragment.Admin;

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

import com.example.vision_app.Adapter.RechargeAdapter;
import com.example.vision_app.DAO.RechargeDao;
import com.example.vision_app.Model.Recharge;
import com.example.vision_app.R;

import java.util.ArrayList;

public class ManageWithdraw_Recharge extends Fragment {
    RecyclerView rcv;
    ArrayList<Recharge> list;
    RechargeAdapter adapter;
    RechargeDao dao;
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
        list = dao.getAll();
        rcv.setAdapter(adapter);
    }

    private void initView(View v) {
        rcv = v.findViewById(R.id.rcv_qldnt);
        dao = new RechargeDao(getContext());
        adapter = new RechargeAdapter(list,getContext());
    }


    @Override
    public void onResume() {
        super.onResume();
        list = dao.getAll();
        adapter = new RechargeAdapter(list,getContext());
        rcv.setAdapter(adapter);
    }
}
