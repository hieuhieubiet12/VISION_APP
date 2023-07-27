package com.example.vision_app;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Date.MyDate;
import com.example.vision_app.Model.User;
import com.example.vision_app.Notification.SetNotification;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;


public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout til_userNew,til_passwordNew1,til_passwordNew2,til_nameNew,til_dateNew,til_nPhoneNew,til_addressNew;
    private TextInputEditText ed_userNew,ed_passwordNew1,ed_passwordNew2,ed_nameNew,ed_dateNew,ed_nPhoneNew,ed_addressNew;
    private Button btnRegister;
    private UserDao dao;
    private User obj;
    private MyDate date;
    private String ns = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        ed_dateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        ed_dateNew.setText(date.toStringVn(calendar.getTime()));
                        ns = date.toString(calendar.getTime());
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkisEmpty()){
                    return;
                }
                if(checkCharacter()){
                    return;
                }
                String userName,passWord1,name,nPhone,date,address;
                userName = ed_userNew.getText().toString();
                passWord1 = ed_passwordNew1.getText().toString();
                name = ed_nameNew.getText().toString();
                nPhone = ed_nPhoneNew.getText().toString();
                date = ns;
                address = ed_addressNew.getText().toString();
                obj = new User(userName,name,passWord1,nPhone,date,address);
                if(dao.insertUser(obj)){
                    clearForm();
                    notifiAdd(obj);

                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    Bundle b = new Bundle();
                    b.putString("userName_new", obj.getUser_name());
                    b.putString("password_new", obj.getPassword());
                    i.putExtras(b);
                    startActivity(i);
                }else {
                    Toast.makeText(RegisterActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initView() {
        til_userNew = findViewById(R.id.edL_taiKhoanNew);
        til_passwordNew1 = findViewById(R.id.edL_matKhaunew);
        til_passwordNew2 = findViewById(R.id.edL_nhapLaiMatKhau);
        til_nameNew = findViewById(R.id.edL_hotennew);
        til_dateNew = findViewById(R.id.edL_ngaysinhnew);
        til_nPhoneNew = findViewById(R.id.edL_sdtnew);
        til_addressNew = findViewById(R.id.edL_diaChinew);
        ed_userNew = findViewById(R.id.ed_taiKhoannew);
        ed_passwordNew1 = findViewById(R.id.ed_matKhaunew);
        ed_passwordNew2 = findViewById(R.id.ed_nhapLaiMatKhau);
        ed_nameNew = findViewById(R.id.ed_hotennew);
        ed_dateNew = findViewById(R.id.ed_ngaysinhnew);
        ed_nPhoneNew = findViewById(R.id.ed_sdtnew);
        ed_addressNew = findViewById(R.id.ed_diaChinew);
        btnRegister = findViewById(R.id.btn_dangKy_KH);
        dao = new UserDao(getApplicationContext());
    }
    public boolean checkisEmpty(){
        boolean isEmpty = false;
        if(ed_nameNew.getText().toString().trim().isEmpty()){
            til_userNew.setError("Vui lòng nhập từ ta khoản!");
            isEmpty = true;
        }else{
            til_userNew.setErrorEnabled(false);
        }
        if(ed_passwordNew1.getText().toString().isEmpty()){
            til_passwordNew1.setError("Vui lòng nhập mật khẩu!");
            isEmpty = true;
        }else{
            til_passwordNew1.setErrorEnabled(false);
        }
        if(ed_passwordNew2.getText().toString().isEmpty()){
            til_passwordNew2.setError("Vui lòng xác nhận mật khẩu!");
            isEmpty = true;
        }else{
            til_passwordNew2.setErrorEnabled(false);
        }
        if(ed_nameNew.getText().toString().trim().isEmpty()){
            til_nameNew.setError("Vui lòng nhập tên!");
            isEmpty = true;
        }else{
            til_nameNew.setErrorEnabled(false);
        }
        if(ed_nPhoneNew.getText().toString().trim().isEmpty()){
            til_nPhoneNew.setError("Vui lòng nhập số điện thoại!");
            isEmpty = true;
        }else{
            til_nPhoneNew.setErrorEnabled(false);
        }
        if(ed_dateNew.getText().toString().trim().isEmpty()){
            til_dateNew.setError("Vui lòng nhập ngày tháng năm sinh!");
            isEmpty = true;
        }else{
            til_dateNew.setErrorEnabled(false);
        }
        if(ed_addressNew.getText().toString().isEmpty()){
            til_addressNew.setError("Vui lòng nhập địa chỉ!");
            isEmpty = true;
        }else{
            til_addressNew.setErrorEnabled(false);
        }
        return isEmpty;
    }
    public boolean checkCharacter(){
        boolean isEmpty = false;
        if(ed_userNew.getText().toString().length()<4){
            til_userNew.setError("Tài khoản tối thiểu 4 ký tự !");
            isEmpty = true;
        }else{
            til_userNew.setErrorEnabled(false);
        }
        if (ed_passwordNew1.getText().toString().length() < 6) {
            til_passwordNew1.setError("Mật khẩu nhập tối thiểu 6 ký tự!");
            isEmpty = true;
        } else {
            til_passwordNew1.setErrorEnabled(false);
        }
        if(ed_passwordNew2.getText().toString().equals(ed_passwordNew1.getText().toString())){
            isEmpty = false;
        }else {
            til_passwordNew2.setError("Mật khẩu xác nhận ko trùng khớp");
            isEmpty = true;
        }
        if (!ed_nPhoneNew.getText().toString().matches("^0[3589]{1}\\d{8}$")) {
            ed_nPhoneNew.setError("Số điện thoại phải đúng định dạng!");
            isEmpty = true;
        } else {
            til_nPhoneNew.setErrorEnabled(false);
        }
        return isEmpty;
    }
    public void clearForm(){
        ed_userNew.setText("");
        ed_passwordNew1.setText("");
        ed_passwordNew2.setText("");
        ed_nPhoneNew.setText("");
        ed_dateNew.setText("");
        ed_nameNew.setText("");
        ed_addressNew.setText("");
    }
    private void notifiAdd(User obj) {
        SetNotification notification = new SetNotification(getApplicationContext());
        notification.sendNotification("Register","Thêm mới "+dao.getUserByUserName(obj.getUser_name()).getName()+ "thành công");
    }
}