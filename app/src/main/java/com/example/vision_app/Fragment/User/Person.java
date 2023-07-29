package com.example.vision_app.Fragment.User;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vision_app.Account_settings;
import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.R;
import com.example.vision_app.VitienActivity;



public class Person extends Fragment {
    private RelativeLayout vitien_other;

    private TextView tv_vitien_ohter,tv_ten_other;
    private ImageView btn_cdtk_person, btn_giohang_person;

    private UserDao dao;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName","");
        dao = new UserDao(getContext());
        int role = dao.getUserByUserName(userName).getRole();



        if(role==0){
            tv_ten_other.setText(dao.getUserByUserName(userName).getName()+" Admin");
        }

        vitien_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), VitienActivity.class));
            }
        });


        btn_cdtk_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),Account_settings.class));

            }
        });


    }

    private void initView(View view) {
        vitien_other = view.findViewById(R.id.item_vitien_person);
        tv_ten_other = view.findViewById(R.id.tv_name_person);
        btn_cdtk_person = view.findViewById(R.id.btn_cdtk_person);
        btn_giohang_person = view.findViewById(R.id.btn_giohang_person);
    }
}
