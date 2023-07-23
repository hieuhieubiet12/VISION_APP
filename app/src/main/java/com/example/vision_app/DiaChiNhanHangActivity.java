package com.example.vision_app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class DiaChiNhanHangActivity extends AppCompatActivity {

    TextInputLayout edL_tenNguoiNhan,edL_dienThoai,edL_diaChi;

    TextInputEditText ted_tenNguoiNhan,ted_dienThoai,ted_diaChi;

    Spinner spin_thanhpho , spin_quanhuyen , spin_phuongxa;

    Button btnLuuDeliAddress;
    private String tenTP,tenPhuong,tenxa;
    private String[] quanhuyen , phuongXa;
    String diaChi , tenNguoiNhan , sdt;
    private ImageView img_exit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diachinhanhang);
        initView();
        tenTP="";
        img_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // spinner thành phố
        String[] cities = {"Tỉnh/Thành phố","Hà Nội", "TP Hồ Chí Minh"};
        String[] spn_rong ={"Quận/huyện"};
        String[] spnn_rong = {"Phường/Xã"};

        String[] quan_HN = {"Quận/Huyện","Quận Ba Đình", "Quận Hai Bà Trưng"};
        String[] quan_HCM = {"Quận/Huyện","Quận 1", "Quận 2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_thanhpho.setAdapter(adapter);

        spin_thanhpho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenTP = parent.getItemAtPosition(position).toString();
                if (position!=0){
                    diaChi = tenTP;
                }
                switch (tenTP){
                    case "Hà Nội":
                        quanhuyen = quan_HN;
                        break;
                    case "TP Hồ Chí Minh":
                        quanhuyen = quan_HCM;
                        break;
                    default:
                        quanhuyen = spn_rong;
                        break;
                }
                loadDataSPNH();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String[] phuong_HBT = {"Phường/Xã","Bạch Đằng","Bạch Mai","Cầu Dền","Đống Mác","Đồng Nhân"};
        String [] phuong_BaDinh = {"Phường/Xã","Cống Vị", "Điện Biên", "Đội Cấn", "Giảng Võ", "Kim Mã"};
        String[] phuong_quan1 = {"Phường/Xã"," Bến Nghé", "Bến Thành", " Cô Giang", " Cầu Kho", "Cầu Ông Lãnh"};
        String[] phuong_quan2 = {"Phường/Xã"," An Khánh ", " An Phú", " Bình An", " Cát Lái ", " Thảo Điền"};

        spin_quanhuyen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenPhuong = parent.getItemAtPosition(position).toString();
                if (position!=0){
                    diaChi = tenPhuong+","+diaChi;
                }
                switch (tenPhuong){
                    case "Quận Ba Đình":
                        phuongXa = phuong_BaDinh;
                        break;
                    case "Quận Hai Bà Trưng":
                        phuongXa = phuong_HBT;
                        break;

                    case "Quận 1":
                        phuongXa = phuong_quan1;
                        break;

                    case "Quận 2":
                        phuongXa = phuong_quan2;
                        break;

                    default:
                        phuongXa = spnn_rong;
                        break;
                }
                loadDataSPNH1();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_phuongxa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tenxa = parent.getItemAtPosition(position).toString();
                if (position!=0){
                    diaChi = tenxa+","+diaChi;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnLuuDeliAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTrong();
                tenNguoiNhan = ted_tenNguoiNhan.getText().toString();
                sdt = ted_dienThoai.getText().toString();
                diaChi = ted_diaChi.getText().toString();
                Toast.makeText(DiaChiNhanHangActivity.this, ""+ tenNguoiNhan + sdt + diaChi, Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void loadDataSPNH(){
        ArrayAdapter<String> adapterquanHuyen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quanhuyen);
        adapterquanHuyen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_quanhuyen.setAdapter(adapterquanHuyen);
    }

    private void loadDataSPNH1(){
        ArrayAdapter<String> adapterphuongXa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, phuongXa);
        adapterphuongXa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_phuongxa.setAdapter(adapterphuongXa);
    }
    private void initView() {
        img_exit = findViewById(R.id.img_exit_dcnh);
        edL_tenNguoiNhan = findViewById(R.id.til_name_deliaddress);
        edL_dienThoai = findViewById(R.id.til_sdt_deliaddress);
        edL_diaChi = findViewById(R.id.til_address_deliaddress);
        ted_tenNguoiNhan = findViewById(R.id.ted_name_deliaddress);
        ted_dienThoai = findViewById(R.id.ted_sdt_deliaddress);
        ted_diaChi = findViewById(R.id.ted_address_deliaddress);
        btnLuuDeliAddress = findViewById(R.id.btn_save_deliaddress);
        spin_thanhpho = findViewById(R.id.spin_thanhpho);
        spin_quanhuyen = findViewById(R.id.spin_quanhuyen);
        spin_phuongxa = findViewById(R.id.spin_phuongxa);
    }

    public boolean checkTrong(){
        boolean isEmpty = false;
        if (ted_tenNguoiNhan.getText().toString().isEmpty()) {
            edL_tenNguoiNhan.setError("Vui lòng nhập tên !");
            isEmpty = true;
        } else {
            edL_tenNguoiNhan.setErrorEnabled(false);
        }
        if (ted_dienThoai.getText().toString().isEmpty()) {
            edL_dienThoai.setError("Vui lòng nhập số điện thoại!");
            isEmpty = true;
        } else {
            edL_dienThoai.setErrorEnabled(false);
        }
        if (ted_diaChi.getText().toString().isEmpty()) {
            edL_diaChi.setError("Vui lòng nhập địa chỉ!");
            isEmpty = true;
        } else {
            edL_diaChi.setErrorEnabled(false);
        }
        return isEmpty;
    }

}
