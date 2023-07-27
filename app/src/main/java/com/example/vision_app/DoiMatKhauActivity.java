package com.example.vision_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class DoiMatKhauActivity extends AppCompatActivity {
    TextInputLayout edL_matKhauCu,edL_matKhauMoi,edL_matKhauMoi2;
    TextInputEditText ed_matKhauCu,ed_matKhauMoi,ed_matKhauMoi2;
    Button btn_dmk;

    ImageView img_exit_dmk;

    int role;
    UserDao dao;
    ArrayList<User> list;

    ImageView btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        dao = new UserDao(this);
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences("remember", Context.MODE_PRIVATE);

        String user_name = sharedPreferences.getString("userName","");

        btn_dmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem các trường nhập liệu có trống hay không

                String currentPassword = ed_matKhauCu.getText().toString().trim();
                String newPassword = ed_matKhauMoi.getText().toString().trim();
                String confirmPassword = ed_matKhauMoi2.getText().toString().trim();
                checkTrong();

//                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
//                    Toast.makeText(DoiMatKhauActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                // Kiểm tra mật khẩu mới có khớp nhau hay không
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu mới không khớp nhau", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra mật khẩu hiện tại có đúng hay không (thực hiện kiểm tra với cơ sở dữ liệu hoặc thông tin người dùng)
                if (!isCurrentPasswordValid(currentPassword)) {
                    Toast.makeText(DoiMatKhauActivity.this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thực hiện cập nhật mật khẩu mới vào cơ sở dữ liệu hoặc thông tin người dùng
                if (dao.updatePasswordByUserName(user_name,newPassword)) {
                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }

            }
            public boolean isCurrentPasswordValid(String currentPassword){
                // Kiểm tra mật khẩu hiện tại trong cơ sở dữ liệu hoặc thông tin người dùng
                String mk_user = dao.getPasswordByUsername(user_name);
                if(mk_user.equals(currentPassword)){
                    // Trả về true nếu mật khẩu hiện tại đúng
                    return true;
                }else {
                    return false;
                }
            }
        });




        img_exit_dmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    private void initView() {
        edL_matKhauCu = findViewById(R.id.til_matKhauCu);
        edL_matKhauMoi = findViewById(R.id.til_matKhauMoi);
        edL_matKhauMoi2 = findViewById(R.id.til_matKhauMoi2);
        ed_matKhauMoi = findViewById(R.id.ted_matKhauMoi);
        ed_matKhauCu = findViewById(R.id.ted_matKhauCu);
        ed_matKhauMoi2 = findViewById(R.id.ted_matKhauMoi2);
        btn_dmk = findViewById(R.id.btn_dmk);
        img_exit_dmk = findViewById(R.id.img_exit_dmk);
        dao = new UserDao(this);
    }
    public boolean checkTrong(){
        boolean isEmpty = false;
        if (ed_matKhauCu.getText().toString().isEmpty()) {
            edL_matKhauCu.setError("Vui lòng nhập mật khẩu cũ !");
            isEmpty = true;
        } else {
            edL_matKhauCu.setErrorEnabled(false);
        }
        if (ed_matKhauMoi.getText().toString().isEmpty()) {
            edL_matKhauMoi.setError("Vui lòng nhập mật khẩu mới!");
            isEmpty = true;
        } else {
            edL_matKhauMoi.setErrorEnabled(false);
        }
        if (ed_matKhauMoi2.getText().toString().isEmpty()) {
            edL_matKhauMoi2.setError("Vui lòng nhập mật khẩu mới!");
            isEmpty = true;
        } else {
            edL_matKhauMoi2.setErrorEnabled(false);
        }
        return isEmpty;
    }

}