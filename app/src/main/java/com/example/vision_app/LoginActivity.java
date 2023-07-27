package com.example.vision_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Model.User;
import com.example.vision_app.Notification.SetNotification;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout til_userName, til_password;
    private TextInputEditText ed_userName, ed_password;
    private CheckBox chk_remember;
    private TextView btnRegister;
    private Button btnLogin;
    private UserDao dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        //Nhận dữ liệu từ RegisterActivity
        Bundle bundle = getIntent().getExtras();
        String userName_new = "";
        String passWord_new = "";
        if(bundle!=null){
             userName_new = bundle.getString("userName_new");
             passWord_new = bundle.getString("password_new");
        }
            ed_userName.setText(userName_new);
            ed_password.setText(passWord_new);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraTrong()) {
                    return;
                }
                if (kiemTraKyTu()) {
                    return;
                }
                String userName = ed_userName.getText().toString();
                String password = ed_password.getText().toString();
                if(dao.checkLogin(userName,password)){
                    SetNotification notification = new SetNotification(LoginActivity.this);
                    //Khởi tạo đối tượng và dùng dao để lấy ra 1 đối tượng
                    User obj = dao.getUserByUserName(userName);
                    //tạo 1 biến role lấy dữ liệu từ đối tượng đã đc lấy ra ở trên
                    int role = obj.getRole();
                    //Nếu role == 0 thì dang nhap bang admin
                    if(role==0){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        notification.sendNotification("Admin Login", obj.getName()+" đã đăng nhập thành công");
                        Toast.makeText(LoginActivity.this, "Chào mừng admin", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Toast.makeText(LoginActivity.this, obj.getName()+" đã đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "Vui lòng kiểm tra lại tài khoản", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Lưu tài khoản
        SharedPreferences sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        chk_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {
                    editor.putString("userName", ed_userName.getText().toString());
                    editor.putString("passWord", ed_password.getText().toString());
                } else {
                    editor.remove("userName");
                    editor.remove("passWord");
                }
                editor.apply();
            }
        });
        String userName = sharedPreferences.getString("userName", "");
        String passWord = sharedPreferences.getString("passWord", "");
        if (!userName.isEmpty() && !passWord.isEmpty()) {
            ed_userName.setText(userName);
            ed_password.setText(passWord);
        }
    }
    //Ánh xạ view
    private void initView() {
        til_password = findViewById(R.id.edL_matKhau);
        til_userName = findViewById(R.id.edL_taiKhoan);
        ed_password = findViewById(R.id.ed_matKhau);
        ed_userName = findViewById(R.id.ed_taiKhoan);
        chk_remember = findViewById(R.id.chk_nhoTaiKhoan);
        btnRegister = findViewById(R.id.btn_dangKy);
        btnLogin = findViewById(R.id.btn_dangNhap);
        dao = new UserDao(getApplicationContext());
    }
    public boolean kiemTraTrong() {
        boolean isEmpty = false;
        if (ed_userName.getText().toString().isEmpty()) {
            til_userName.setError("Vui lòng nhập tài khoản!");
            isEmpty = true;
        } else {
            til_userName.setErrorEnabled(false);
        }
        if (ed_password.getText().toString().isEmpty()) {
            til_password.setError("Vui lòng nhập mật khẩu!");
            isEmpty = true;
        } else {
            til_password.setErrorEnabled(false);
        }
        return isEmpty;
    }

    public boolean kiemTraKyTu() {
        boolean isEmpty = false;
        if (ed_userName.getText().toString().length() < 4) {
            til_userName.setError("Tài khoản nhập tối thiểu 4 ký tự!");
            isEmpty = true;
        } else {
            til_userName.setErrorEnabled(false);
        }
        if (ed_password.getText().toString().length() < 6) {
            til_password.setError("Mật khẩu nhập tối thiểu 6 ký tự!");
            isEmpty = true;
        } else {
            til_password.setErrorEnabled(false);
        }
        return isEmpty;
    }
}