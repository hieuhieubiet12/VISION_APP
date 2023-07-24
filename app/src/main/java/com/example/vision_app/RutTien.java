package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.RechargeDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.UserDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.WithdrawDAO;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Recharge;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Withdraw;

public class RutTien extends AppCompatActivity {
    private TextView id_Rut,ten_Rut,tien_Rut,time_Rut,trangThai_Rut;
    private Button btn_xacNhanRut,btn_huyRut;
    private ImageView btn_back_ctdRut;
    private UserDao userDao;
    private Withdraw withdraw;
    private WithdrawDAO withdrawDAO;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rut_tien);
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences("remember", Context.MODE_PRIVATE);
        String user_name = sharedPreferences.getString("userName","");
        int role = userDao.getUserByUserName(user_name).getRole();
        withdrawDAO=new WithdrawDAO(this);
        Bundle bundle = getIntent().getExtras();
        //du lieu user được lấy từ adapter và gán vào đối tượng objRecharge
        int id_dn = bundle.getInt("id_withdraw_extra");

        Withdraw objWithdraw=new Withdraw();

        objWithdraw.setId_withdraw(bundle.getInt("id_withdraw_extra"));
        objWithdraw.setWithdraw_amount(bundle.getInt("with_amount_extra"));
        objWithdraw.setStatus_withdraw(bundle.getInt("statusWith_extra"));
        objWithdraw.setUser_name(bundle.getString("userName_extra"));
        objWithdraw.setDate_withdraw(bundle.getString("timeWith_extra"));



        // set lại dữ liệu đã nhập trc đó cho ui
        id_Rut.setText(String.valueOf(objWithdraw.getId_withdraw()));
        ten_Rut.setText(userDao.getUserByUserName(objWithdraw.getUser_name()).getName());
        time_Rut.setText(objWithdraw.getDate_withdraw());
        tien_Rut.setText(String.valueOf(objWithdraw.getWithdraw_amount()));


        btn_back_ctdRut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Biến tt được gán dữ liệu trạng thái của đơn nạp.
        int tt = objWithdraw.getStatus_withdraw();
        /*Nếu tt nhận bằng 0 thì textview trạng thái in ra 'chờ xác nhận',
                           1 thì in ra 'thành công'
                           còn lại là 'hủy'.
        */
        if(tt==0){
            trangThai_Rut.setText("Chờ xác nhận");
            trangThai_Rut.setTextColor(R.color.red);
        }else if(tt==1){
            trangThai_Rut.setText("Thành công");
            trangThai_Rut.setTextColor(R.color.success);
        }else {
            trangThai_Rut.setText("Hủy");
        }

        //Quyền Admin.
        if (role == 0){
            //Nếu role bằng 0 thì check trạng thái đơn.
            if (objWithdraw.getStatus_withdraw()!=0){
                //Nếu trạng thái đơn khác 0 ( nghĩa là đơn đã được xác nhận).
                //Và ẩn 2 nút của xác nhận , hủy của admin.
                btn_xacNhanRut.setVisibility(View.GONE);
                btn_huyRut.setVisibility(View.GONE);
                return;
            }
            //Admin thao tác với nút xác nhận.
            btn_xacNhanRut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                     * 1.Cập nhật lại stk của user trong csdl (tiền nạp user + số dư tk user, theo tài khoản đăng nhập)
                     * 2.Cập nhật lại trạng thái đơn nạp (tt = 1, theo mã đơn nạp)
                     * tt == 1 tức là đơn đã xác nhận thành công
                     * */
                    if(withdrawDAO.updateStatusWithdraw(1,objWithdraw.getId_withdraw())){
                        userDao.updateMoney( objWithdraw.getUser_name(),objWithdraw.getWithdraw_amount()- userDao.getSoDuTK(objWithdraw.getUser_name()));
                        Toast.makeText(RutTien.this, "Xác nhận thành công!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(RutTien.this, "Xác nhận không thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            btn_huyRut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                     * Cập nhật lại trạng thái đơn nạp (tt = 2, theo mã đơn rut)
                     * tt == 2 tức là đơn nạp sẽ hiển thị là hủy
                     * */

                    if(withdrawDAO.updateStatusWithdraw(2,objWithdraw.getId_withdraw())){
                        withdrawDAO.deleteWithdraw( objWithdraw.getId_withdraw());
                        Toast.makeText(RutTien.this, "Hủy thành công!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        Toast.makeText(RutTien.this, "Hủy không thành công!", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        //Quyền User
        if (role == 1){
            btn_xacNhanRut.setText("Sửa");
            btn_huyRut.setText("Xóa");
            if (objWithdraw.getStatus_withdraw()!=0){
                btn_xacNhanRut.setVisibility(View.GONE);
//                btn_huy.setVisibility(View.GONE);
            }
            btn_xacNhanRut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(RutTien.this);
                    dialog.setContentView(R.layout.dialog_ruttien);
                    TextView tv_dialog_RT = dialog.findViewById(R.id.tv_dialog_rutTien);
                    TextInputLayout edL_soTienRut = dialog.findViewById(R.id.edL_soTienRut);
                    TextInputEditText ed_soTienRut = dialog.findViewById(R.id.ed_soTienRut);
                    Button btn_xacNhanRut_dialog = dialog.findViewById(R.id.btn_xacNhanRutTien);
                    tv_dialog_RT.setText("Chỉnh sửa đơn rút tiền");
                    ed_soTienRut.setText(objWithdraw.getWithdraw_amount()+"");

                    btn_xacNhanRut_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Date currentDate = new Date();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
                            String time2 = dateFormat.format(currentDate.getTime());
                            withdraw.setId_withdraw(objWithdraw.getId_withdraw());
                            withdraw.setUser_name(objWithdraw.getUser_name());
                            withdraw.setDate_withdraw(time2);
                            withdraw.setStatus_withdraw(0);
                            withdraw.setWithdraw_amount(Integer.parseInt(ed_soTienRut.getText().toString().trim()));
                            if(withdrawDAO.updateWithdraw(withdraw)){
                                Toast.makeText(RutTien.this, "Thành công", Toast.LENGTH_SHORT).show();
                                tien_Rut.setText(ed_soTienRut.getText().toString().trim());
                                time_Rut.setText(time2);
                                dialog.dismiss();
                            }else {
                                Toast.makeText(RutTien.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    dialog.show();
                }

            });
            btn_huyRut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RutTien.this);

                    builder.setMessage("Bạn có chắc chắn muốn hủy đơn nạp tiền này không?");

                    builder.setTitle("Thông báo");

                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(withdrawDAO.deleteWithdraw(objWithdraw.getId_withdraw())){
                                Toast.makeText(RutTien.this, "Hủy thành công", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                                onBackPressed();
                            }else {
                                Toast.makeText(RutTien.this, "Hủy thất bại", Toast.LENGTH_SHORT).show();
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

    private void initView() {
        id_Rut = findViewById(R.id.tv_id_ctdRut);
        btn_back_ctdRut = findViewById(R.id.btn_back_ctdrut);
        userDao = new UserDao(getApplicationContext());
        withdrawDAO = new WithdrawDAO(getApplicationContext());
        withdraw=new Withdraw();
        ten_Rut = findViewById(R.id.tv_hoTen_ctdRut);
        tien_Rut = findViewById(R.id.tv_soTien_ctdRut);
        time_Rut = findViewById(R.id.tv_time_ctdRut);
        trangThai_Rut = findViewById(R.id.tv_trangThai_ctdRut);
        btn_xacNhanRut = findViewById(R.id.btn_xacNhan_ctdRut);
        btn_huyRut = findViewById(R.id.btn_huy_ctdRut);
    }



}