package com.example.vision_app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Date.MyDate;
import com.example.vision_app.Model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class Activity_personal_information extends AppCompatActivity {
    private RelativeLayout rl_ten , rl_sdt ,rl_ngaySinh , rl_diaChi;
    private TextView tv_name_ttcn,tv_nametitile_ttcn , tv_sdt_ttcn , tv_address_ttcn , tv_date_ttcn;
    private ImageView img_avatar , img_exit;
    private UserDao dao;

    private MyDate date;

    String ns = "";
    private Button btn_luuthaydoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        initView();
        SharedPreferences preferences = getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName","");
        dao = new UserDao(this);
        loadData(userName);

        img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // sửa tên
        rl_ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo dialog
                Dialog dialog = new Dialog(Activity_personal_information.this);
                dialog.setContentView(R.layout.dialog_ten);
                TextView title_sua_tt = dialog.findViewById(R.id.title_sua_tt);
                TextInputLayout til_suaten = dialog.findViewById(R.id.til_dialog_ten);
                TextInputEditText ted_suaten = dialog.findViewById(R.id.ted_dialog_ten);
                Button btnsuaTen = dialog.findViewById(R.id.btn_sua_tenDialog);
                Button btndongTen = dialog.findViewById(R.id.btn_dong_tenDialog);
                title_sua_tt.setText("Sửa tên");
                ted_suaten.setHint("Nhập họ tên...");
                btndongTen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnsuaTen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ted_suaten.getText().toString().isEmpty()){
                            til_suaten.setError("Vui lòng tên");
                            return;
                        }else {
                            til_suaten.setError("");
                        }
                        dao.updateTen(userName,ted_suaten.getText().toString());
                        loadData(userName);
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
        // Sửa sdt
        rl_sdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo dialog
                Dialog dialog = new Dialog(Activity_personal_information.this);
                dialog.setContentView(R.layout.dialog_ten);
                TextView title_sua_tt = dialog.findViewById(R.id.title_sua_tt);
                TextInputLayout til_suaten = dialog.findViewById(R.id.til_dialog_ten);
                TextInputEditText ted_suaten = dialog.findViewById(R.id.ted_dialog_ten);
                Button btnsuaTen = dialog.findViewById(R.id.btn_sua_tenDialog);
                Button btndongTen = dialog.findViewById(R.id.btn_dong_tenDialog);
                title_sua_tt.setText("Sửa số điện thoại");
                ted_suaten.setHint("Nhập số điện thoại...");
                btndongTen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnsuaTen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ted_suaten.getText().toString().isEmpty()){
                            til_suaten.setError("Vui lòng nhập số điện thoại");
                            return;
                        }else {
                            til_suaten.setError("");
                        }
                        dao.updateSdt(userName,ted_suaten.getText().toString());
                        loadData(userName);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        // Sửa địa chỉ
        rl_diaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo dialog
                Dialog dialog = new Dialog(Activity_personal_information.this);
                dialog.setContentView(R.layout.dialog_ten);
                TextView title_sua_tt = dialog.findViewById(R.id.title_sua_tt);
                TextInputLayout til_suaten = dialog.findViewById(R.id.til_dialog_ten);
                TextInputEditText ted_suaten = dialog.findViewById(R.id.ted_dialog_ten);
                Button btnsuaTen = dialog.findViewById(R.id.btn_sua_tenDialog);
                Button btndongTen = dialog.findViewById(R.id.btn_dong_tenDialog);
                title_sua_tt.setText("Sửa địa chỉ");
                ted_suaten.setHint("Nhập địa chỉ...");
                btndongTen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnsuaTen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ted_suaten.getText().toString().isEmpty()){
                            til_suaten.setError("Vui lòng nhập địa chỉ");
                            return;
                        }else {
                            til_suaten.setError("");
                        }
                        dao.updatediaChi(userName,ted_suaten.getText().toString());
                        loadData(userName);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        // Sửa ngày sinh
        rl_ngaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tạo dialog
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Activity_personal_information.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        String ngaySinh = day + "/" + (month + 1) + "/" + year; // Định dạng ngày tháng năm
                        // Gọi phương thức updateNgaySinh(userName, ngaySinh) để cập nhật ngày sinh vào cơ sở dữ liệu
                        dao.updateNgaySinh(userName, ngaySinh);
                        loadData(userName);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        btn_luuthaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Activity_personal_information.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });


    }



    private void initView() {
        rl_ten = findViewById(R.id.rl_ten);
        tv_name_ttcn = findViewById(R.id.tv_name_ttcn);
        tv_nametitile_ttcn = findViewById(R.id.tv_nametitile_ttcn);
        rl_sdt = findViewById(R.id.rl_sdt);
        rl_ngaySinh = findViewById(R.id.rl_ngaySinh);
        rl_diaChi = findViewById(R.id.rl_diaChi);
        btn_luuthaydoi = findViewById(R.id.btn_luuthaydoi);
        img_avatar = findViewById(R.id.avatar_chitiet_ttcn);
        img_exit = findViewById(R.id.img_exit_ttcn);
        tv_sdt_ttcn = findViewById(R.id.tv_sdt_ttcn);
        tv_address_ttcn = findViewById(R.id.tv_address_ttcn);
        tv_date_ttcn = findViewById(R.id.tv_date_ttcn);
    }
    private  void loadData(String userName){
        User user = dao.getUserByUserName(userName);
        tv_name_ttcn.setText(user.getName().toString());
        tv_nametitile_ttcn.setText(user.getName().toString());
        tv_sdt_ttcn.setText(user.getN_phone().toString());
        tv_address_ttcn.setText(user.getAddress().toString());
        tv_date_ttcn.setText(user.getDate().toString());
    }

}
