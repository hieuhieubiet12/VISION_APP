package com.example.vision_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vision_app.DAO.RechargeDao;
import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Model.Recharge;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReachargeDetail extends AppCompatActivity {
    private TextView id,ten,tien,time,trangThai;
    private ImageView img_hoadon,btn_back_ctdn,anhHD;
    private Button btn_xacNhan,btn_huy;
    private UserDao userDao;
    private Recharge recharge;
    private RechargeDao rechargeDao;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reacharge_detail);
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences("remember", Context.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("userName","");
        int role = userDao.getUserByUserName(user_name).getRole();
        rechargeDao = new RechargeDao(this);
        Bundle bundle = getIntent().getExtras();
        //du lieu user được lấy từ adapter và gán vào đối tượng objRecharge
        int id_dn = bundle.getInt("id_recharge_extra");
        Log.d("hhhhhhhh", "iddd: "+id_dn);

        Recharge objRecharge = new Recharge();
        objRecharge.setId_recharge(bundle.getInt("id_recharge_extra"));
        objRecharge.setRechare_amount(bundle.getDouble("recharge_amount_extra"));
        objRecharge.setStatus_recharge(bundle.getInt("status_extra"));

        objRecharge.setUser_name(bundle.getString("userName_extra"));
        objRecharge.setDate_recharge(bundle.getString("time_extra"));

        //ảnh được lấy qua hàm getImageById() bằng id_dn và được set lại vào objRecharge
        byte[] bytes =rechargeDao.getImageById(id_dn);
        Log.d("hhhhhhhh", "anh: "+bytes);
        objRecharge.setRecceipt_image(bytes);

        // set lại dữ liệu đã nhập trc đó cho ui
        id.setText(String.valueOf(objRecharge.getId_recharge()));
        ten.setText(userDao.getUserByUserName(objRecharge.getUser_name()).getName());
        time.setText(objRecharge.getDate_recharge());
        tien.setText(String.valueOf(objRecharge.getRechare_amount()));

        if (objRecharge != null && objRecharge.getRecceipt_image() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(objRecharge.getRecceipt_image(), 0, objRecharge.getRecceipt_image().length);
            img_hoadon.setImageBitmap(bitmap);

        } else {
            // Xử lý trường hợp objRecharge hoặc recceipt_image là null
            Toast.makeText(this, "ko co anh", Toast.LENGTH_SHORT).show();
        }

        btn_back_ctdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Biến tt được gán dữ liệu trạng thái của đơn nạp.
        int tt = objRecharge.getStatus_recharge();
        /*Nếu tt nhận bằng 0 thì textview trạng thái in ra 'chờ xác nhận',
                           1 thì in ra 'thành công'
                           còn lại là 'hủy'.
        */
        if(tt==0){
            trangThai.setText("Chờ xác nhận");
            trangThai.setTextColor(R.color.red);
        }else if(tt==1){
            trangThai.setText("Thành công");
            trangThai.setTextColor(R.color.success);
        }else {
            trangThai.setText("Hủy");
        }

        //Quyền Admin.
        if (role == 0){
            //Nếu role bằng 0 thì check trạng thái đơn.
            if (objRecharge.getStatus_recharge()!=0){
                //Nếu trạng thái đơn khác 0 ( nghĩa là đơn đã được xác nhận).
                //Và ẩn 2 nút của xác nhận , hủy của admin.
                btn_xacNhan.setVisibility(View.GONE);
                btn_huy.setVisibility(View.GONE);
                return;
            }
            //Admin thao tác với nút xác nhận.
            btn_xacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    * 1.Cập nhật lại stk của user trong csdl (tiền nạp user + số dư tk user, theo tài khoản đăng nhập)
                    * 2.Cập nhật lại trạng thái đơn nạp (tt = 1, theo mã đơn nạp)
                    * tt == 1 tức là đơn đã xác nhận thành công
                    * */
                    if(rechargeDao.updateStatusRecharge(1,objRecharge.getId_recharge())){
                        userDao.updateMoney( objRecharge.getUser_name(),objRecharge.getRechare_amount() + userDao.getSoDuTK(objRecharge.getUser_name()));
                        Toast.makeText(ReachargeDetail.this, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(ReachargeDetail.this, "Xác nhận không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btn_huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                     * Cập nhật lại trạng thái đơn nạp (tt = 2, theo mã đơn nạp)
                     * tt == 2 tức là đơn nạp sẽ hiển thị là hủy
                     * */
                    if(rechargeDao.updateStatusRecharge(2,objRecharge.getId_recharge())){
                        rechargeDao.deleteRecharge(objRecharge.getId_recharge());
                        Toast.makeText(ReachargeDetail.this, "Hủy thành công!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(ReachargeDetail.this, "Hủy không thành công!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        //Quyền User
        if (role == 1){
            btn_xacNhan.setText("Sửa");
            btn_huy.setText("Xóa");
            if (objRecharge.getStatus_recharge()!=0){
                btn_xacNhan.setVisibility(View.GONE);
                btn_huy.setVisibility(View.GONE);
                return;
            }
            btn_xacNhan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(ReachargeDetail.this);
                    dialog.setContentView(R.layout.dialog_recharge);
                    TextView tv_themAnh = dialog.findViewById(R.id.tv_themAnh);
                    TextView tv_dialog_nt = dialog.findViewById(R.id.tv_dialog_nt);
                    TextInputLayout edL_soTien = dialog.findViewById(R.id.edL_soTienNap);
                    TextInputEditText ed_soTien = dialog.findViewById(R.id.ed_soTienNap);
                    Button btn_xacNhan_dialog = dialog.findViewById(R.id.btn_xacNhanNapTien);
                    anhHD = dialog.findViewById(R.id.img_anhHD);
                    tv_dialog_nt.setText("Chỉnh sửa đơn nạp tiền");
                    ed_soTien.setText(objRecharge.getRechare_amount()+"");
                    anhHD.setVisibility(View.GONE);
                    tv_themAnh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            anhHD.setVisibility(View.VISIBLE);
                        }
                    });
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
                    btn_xacNhan_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Date currentDate = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                            String time1 = dateFormat.format(currentDate.getTime());
                            recharge.setId_recharge(objRecharge.getId_recharge());
                            recharge.setUser_name(objRecharge.getUser_name());
                            recharge.setDate_recharge(time1);
                            recharge.setStatus_recharge(0);
                            recharge.setRechare_amount(Double.parseDouble(ed_soTien.getText().toString().trim()));
                            if(rechargeDao.updateRecharge(recharge)){
                                Toast.makeText(ReachargeDetail.this, "Thành công", Toast.LENGTH_SHORT).show();
                                tien.setText(ed_soTien.getText().toString().trim());
                                time.setText(time1);
                                dialog.dismiss();
                            }else {
                                Toast.makeText(ReachargeDetail.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                }

            });
            btn_huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReachargeDetail.this);

                    builder.setMessage("Bạn có chắc chắn muốn hủy đơn nạp tiền này không?");

                    builder.setTitle("Thông báo");

                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(rechargeDao.deleteRecharge(objRecharge.getId_recharge())){
                                Toast.makeText(ReachargeDetail.this, "Hủy thành công", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                onBackPressed();
                            }else {
                                Toast.makeText(ReachargeDetail.this, "Hủy thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    builder.setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });
        }
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
    private void initView() {
        id = findViewById(R.id.tv_id_ctdnt);
        btn_back_ctdn = findViewById(R.id.btn_back_ctdn);
        userDao = new UserDao(getApplicationContext());
        rechargeDao = new RechargeDao(getApplicationContext());
        recharge = new Recharge();
        ten = findViewById(R.id.tv_hoTen_ctdnt);
        tien = findViewById(R.id.tv_soTien_ctdnt);
        time = findViewById(R.id.tv_time_ctdnt);
        trangThai = findViewById(R.id.tv_trangThai_ctdnt);
        img_hoadon = findViewById(R.id.img_anhHD_ctdnt);
        btn_xacNhan = findViewById(R.id.btn_xacNhan_ctdnt);
        btn_huy = findViewById(R.id.btn_huy_ctdnt);
    }

}