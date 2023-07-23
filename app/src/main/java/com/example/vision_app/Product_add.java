package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter.SpinnerProTypeAdapter;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.ProductDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.ProductTypeDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Product;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.ProductType;

public class Product_add extends AppCompatActivity {
    private TextInputLayout til_namePro,til_describePro;
    private TextInputEditText ed_namePro,ed_describPro;
    private EditText ed_price;
    private AppCompatSpinner spn_proType;
    private ImageButton btn_upSL,btn_dowSL;
    private TextView tv_Sl;
    private ImageView img_Product,btn_back;
    private Button btn_Add;
    private Product product;
    private ProductType productType;
    private ProductTypeDao proTypeDao;
    private ProductDao productDao;
    private int sl_Default = 0;
    private byte[] anhResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        initView();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        String nameSP = ed_namePro.getText().toString();
//        String describePro = ed_describPro.getText().toString();

        //nút tăng số lượng sp
        btn_upSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sl_Default++;
                tv_Sl.setText(String.valueOf(sl_Default));
            }
        });
        //nút tăng số lượng sp
        btn_dowSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sl_Default>0){
                    sl_Default--;
                }
                tv_Sl.setText(String.valueOf(sl_Default));
            }
        });
        //spinner mã loại sp
        fillSpinner(spn_proType);
        int maLoai = getProType(spn_proType);

        //lấy ảnh ở trong thư viện điện thoại admin
        img_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lấy nội dung của máy admin
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //setType("image") chỉ lấy ảnh
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkTrong()){
                    return;
                }else {
//            Product(int idType, String name, double price, byte[] image, int quantity, String description)
                    Double pricePro = Double.valueOf(ed_price.getText().toString());
                    if(productDao.insertPro(new Product(maLoai,ed_namePro.getText().toString(),pricePro,anhResult,Integer.valueOf(tv_Sl.getText().toString()),ed_describPro.getText().toString()))>0){
                        Toast.makeText(Product_add.this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Product_add.this, "Thêm thất bại.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    private int getProType(Spinner spinner) {
        productType = (ProductType) spinner.getSelectedItem();
        return productType.getId_type_product();
    }


    private void initView() {
        til_describePro = findViewById(R.id.til_add_describe_product);
        til_namePro = findViewById(R.id.til_add_nameProduct);
        ed_namePro = findViewById(R.id.ed_add_nameProduct);
        ed_describPro = findViewById(R.id.ed_add_describe_product);
        ed_price = findViewById(R.id.ed_price_product);
        spn_proType = findViewById(R.id.spn_ProType);
        btn_dowSL = findViewById(R.id.btn_dowSL_add);
        btn_upSL = findViewById(R.id.btn_upSL_add);
        btn_back = findViewById(R.id.btn_back_addProduct);
        tv_Sl = findViewById(R.id.tv_Sl);
        img_Product = findViewById(R.id.img_Product);
        btn_Add = findViewById(R.id.btn_Add_Product);
        product = new Product();
        productType = new ProductType();
        proTypeDao = new ProductTypeDao(getApplicationContext());
        productDao = new ProductDao(getApplicationContext());
    }
    private void fillSpinner(Spinner spinner) {
        SpinnerProTypeAdapter arrayAdapter = new SpinnerProTypeAdapter(getApplicationContext(), proTypeDao.getAllProType());
        spinner.setAdapter(arrayAdapter);
    }
    private boolean checkTrong(){
        boolean isEmpty = false;
        if(ed_namePro.getText().toString().isEmpty()){
            til_namePro.setError("Vui lòng tên sản phẩm !");
            isEmpty = true;
        }else{
            til_namePro.setErrorEnabled(false);
        }
        if(ed_price.getText().toString().isEmpty()){
            ed_price.setError("Vui lòng giá sản phẩm !");
            isEmpty = true;
        }else{
            ed_price.setError("");
        }
        try {
            if(product.getImage().length == 0){
                Toast.makeText(getApplicationContext(), "Vui lòng chọn ảnh", Toast.LENGTH_SHORT).show();
                isEmpty = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isEmpty;
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("rrrrrrr", "data: "+data);
        if(requestCode == 1 &&  resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imgUri = data.getData();
            Log.d("zzzzzzz", "uri: "+imgUri);
            img_Product.setImageURI(imgUri);

            InputStream inputStream = null;
            try {
                inputStream = getApplicationContext().getContentResolver().openInputStream(imgUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer,0,bytesRead);
                }
                byte[] bytes = byteArrayOutputStream.toByteArray();
//                product.setImage(bytes);
                anhResult = bytes;
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        }
    }
}