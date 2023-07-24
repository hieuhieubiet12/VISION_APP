package com.example.vision_app.Fragment.User;

import android.content.Context;
import android.content.Intent;
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

import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.LoginActivity;
import com.example.vision_app.R;
import com.example.vision_app.VitienActivity;

public class Person extends Fragment {
    private RelativeLayout vitien_other,dangxuat_other;
    private TextView tv_vitien_ohter,tv_ten_other;

    private UserDao dao;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person, container, false);
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName","");
        dao = new UserDao(getContext());
        int role = dao.getUserByUserName(userName).getRole();

        vitien_other = view.findViewById(R.id.vitien_other);
        dangxuat_other = view.findViewById(R.id.dangXuat_other);
        tv_vitien_ohter = view.findViewById(R.id.tv_vitien_ohter);
        tv_ten_other = view.findViewById(R.id.tv_ten_other);

        if(role==0){
            tv_ten_other.setText(dao.getUserByUserName(userName).getName()+" Admin");
        }

        vitien_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), VitienActivity.class));
            }
        });

        dangxuat_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
    }

}
