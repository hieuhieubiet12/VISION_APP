package com.example.vision_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Account_settings extends AppCompatActivity {

    private RelativeLayout rlthongtin_cn , rl_diachi_nhanhang , rl_capnhat_pass ;
    private ImageView img_exit;
    private Button btn_dangxuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        initView();
        btn_dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account_settings.this, LoginActivity.class));
            }
        });

        img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rlthongtin_cn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account_settings.this,Activity_personal_information.class));
            }
        });

        rl_capnhat_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account_settings.this,DoiMatKhauActivity.class));

            }
        });

        rl_diachi_nhanhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Account_settings.this,DiaChiNhanHangActivity.class));
            }
        });
    }

    private void initView() {
        btn_dangxuat = findViewById(R.id.btn_dangxuat);
        img_exit = findViewById(R.id.img_exit_cdtk);
        rlthongtin_cn = findViewById(R.id.rl_cdtk_1);
        rl_diachi_nhanhang = findViewById(R.id.rl_cdtk_2);
        rl_capnhat_pass = findViewById(R.id.rl_cdtk_3);
    }
}
