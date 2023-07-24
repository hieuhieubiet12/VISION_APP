package com.example.vision_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vision_app.Adapter.RechargeAdapter;
import com.example.vision_app.DAO.RechargeDao;
import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Model.Recharge;
import com.example.vision_app.Notification.SetNotification;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
public class VitienActivity extends AppCompatActivity{
    private FloatingActionButton btnMenuAdd,btnRecharge,btnWithdraw;
    private TextView tvSoduTK,tvHoTen_vi;
    private ImageView btn_back_vt,anhHD;
    private RecyclerView ryc_vitien;
    private SetNotification notification;
    private Recharge recharge;
    private RechargeDao rechargeDao;
    private UserDao userDao;
    private ArrayList<Recharge> list;
    private RechargeAdapter adpter;
    private Animation alphaFloatingAction,alphaFloatingAction2;
    private boolean isShow = false;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitien);
        intitView();
        SharedPreferences sharedPreferences = getSharedPreferences("remember", Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("userName","");
        tvHoTen_vi.setText(userDao.getUserByUserName(userName).getName());
        tvSoduTK.setText(String.valueOf(userDao.getUserByUserName(userName).getMoney()));
        //loát dữ liệu lịch sử gd
        loadData(userName);

        btn_back_vt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //show nút sự kiện
        btnMenuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = !isShow;
                if(isShow){
                    btnMenuAdd.animate().rotation(-90).setDuration(500).start();
                  btnRecharge.setVisibility(View.VISIBLE);
                  btnWithdraw.setVisibility(View.VISIBLE);
                  btnRecharge.startAnimation(alphaFloatingAction);
                  btnWithdraw.startAnimation(alphaFloatingAction);
                }else {
                    btnMenuAdd.animate().rotation(0).setDuration(500).start();
                    btnRecharge.startAnimation(alphaFloatingAction2);
                    btnWithdraw.startAnimation(alphaFloatingAction2);
                    btnRecharge.setVisibility(View.INVISIBLE);
                    btnWithdraw.setVisibility(View.INVISIBLE);
                }
            }
        });

        //set sk cho nút thêm đơn nạp
        btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tạo dialog
                Dialog dialog = new Dialog(VitienActivity.this);
                dialog.setContentView(R.layout.dialog_recharge);
                TextView tv_themAnh = dialog.findViewById(R.id.tv_themAnh);
                TextInputLayout til_soTien = dialog.findViewById(R.id.edL_soTienNap);
                TextInputEditText ed_soTien = dialog.findViewById(R.id.ed_soTienNap);
                Button btn_xacNhan = dialog.findViewById(R.id.btn_xacNhanNapTien);
                anhHD = dialog.findViewById(R.id.img_anhHD);

                //lấy ảnh ở trong thư viện của user
                anhHD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lấy nội dung của máy người dùng
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        //setType("image") chỉ lấy ảnh
                        intent.setType("image/*");
                        startActivityForResult(intent,1);
                    }
                });
                //sk nguoi dung dk don nap
                btn_xacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Date currentDate = new Date();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
                        String time = dateFormat.format(currentDate.getTime());

                        if(ed_soTien.getText().toString().isEmpty()){
                            til_soTien.setError("Vui lòng nhập số tiền nạp!");
                            return;
                        }else{
                            til_soTien.setErrorEnabled(false);
                        }

                        recharge.setDescribe_recharge("Nạp tiền");
                        recharge.setUser_name(userName);
                        recharge.setDate_recharge(time);
                        recharge.setRechare_amount(Double.parseDouble(ed_soTien.getText().toString().trim()));

                        if(rechargeDao.insertRecharge(recharge)){
                            Toast.makeText(VitienActivity.this, "DK Thành công", Toast.LENGTH_SHORT).show();
                            notification.sendNotification("Đăng ký đơn nạp","Bạn đã đăng ký đơn nạp thành công,\n hãy chờ xác nhận từ admin");
                            loadData(userName);
                            Log.d("img","data_img:"+recharge.getRecceipt_image());
                            dialog.dismiss();
                        }else {
                            Toast.makeText(VitienActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void intitView() {
        btnMenuAdd = findViewById(R.id.btn_menu_add);
        btnRecharge = findViewById(R.id.btn_recharge);
        btnWithdraw = findViewById(R.id.btn_withdraw);
        tvSoduTK = findViewById(R.id.tv_soDuVi);
        tvHoTen_vi = findViewById(R.id.tv_hoten_vi);
        ryc_vitien = findViewById(R.id.rcv_vitien);
        btn_back_vt = findViewById(R.id.btn_back_vt);
        alphaFloatingAction = AnimationUtils.loadAnimation(this,R.anim.floating_alpha);
        alphaFloatingAction2 = AnimationUtils.loadAnimation(this,R.anim.floating_alpha2);

        recharge = new Recharge();
        rechargeDao = new RechargeDao(getApplicationContext());
        userDao = new UserDao(getApplicationContext());
        list = new ArrayList<>();
        notification = new SetNotification(VitienActivity.this);
    }
    public void loadData(String userName){
        list = rechargeDao.getByName(userName);
        adpter = new RechargeAdapter(list,VitienActivity.this);
        ryc_vitien.setAdapter(adpter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("rrrrrrr", "data: "+data);
        if(requestCode == 1 &&  resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imgUri = data.getData();
            Log.d("zzzzzzz", "uri: "+imgUri);
            anhHD.setImageURI(imgUri);

            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(imgUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer,0,bytesRead);
                }
                byte[] bytes = byteArrayOutputStream.toByteArray();
                recharge.setRecceipt_image(bytes);
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
            super.onResume();
            loadData(userName);
    }
}