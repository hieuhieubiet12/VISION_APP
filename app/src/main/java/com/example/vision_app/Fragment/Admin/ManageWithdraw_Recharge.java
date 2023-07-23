package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.Admin;

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
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.RechargeDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Recharge;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.R;

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
