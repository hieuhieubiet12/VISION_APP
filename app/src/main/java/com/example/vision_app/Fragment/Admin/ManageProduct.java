package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Fragment.Admin;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter.ProTypeAdapter;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter.ProductAdapter;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.ProductDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.ProductTypeDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Product;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.ProductType;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Product_add;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.R;

public class ManageProduct extends Fragment {
    private FloatingActionButton btnMenuAdd,btnAddProType,btnAddProduct;
    private Animation alphaFloatingAction,alphaFloatingAction2;
    private boolean isShow = false;
    private ProductType objProductType;
    private ProductTypeDao productTypeDao;

    private ProductDao productDao;
    private Product product;
    private ImageView btn_add_image;
    private RecyclerView ryc_ProType,ryc_Product;
    private ProTypeAdapter proTypeAdapter;
    private ProductAdapter productAdapter;
    private ArrayList<ProductType> listProType;
    private ArrayList<Product> listProduct;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__manage_product,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadDataProType();
        loadDataProduct();
        btnMenuAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShow = !isShow;
                if(isShow){
                    btnMenuAdd.animate().rotation(-90).setDuration(500).start();
                    btnAddProType.setVisibility(View.VISIBLE);
                    btnAddProduct.setVisibility(View.VISIBLE);
                    btnAddProType.startAnimation(alphaFloatingAction);
                    btnAddProduct.startAnimation(alphaFloatingAction);
                }else {
                    btnMenuAdd.animate().rotation(0).setDuration(500).start();
                    btnAddProType.startAnimation(alphaFloatingAction2);
                    btnAddProduct.startAnimation(alphaFloatingAction2);
                    btnAddProType.setVisibility(View.INVISIBLE);
                    btnAddProduct.setVisibility(View.INVISIBLE);
                }
            }
        });
        //xk produc_type
        btnAddProType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_produc_type);
                TextInputLayout tilNameProType = dialog.findViewById(R.id.til_add_nameTypeProduct);
                TextInputEditText edNameProType = dialog.findViewById(R.id.ed_add_nameTypeProduct);
                Button btnAdd = dialog.findViewById(R.id.btn_Add_ProType_dialog);
                Button btnCancel = dialog.findViewById(R.id.btn_Cancel_ProType_dialog);
                btn_add_image = dialog.findViewById(R.id.imgTypeProduct_dialog);
                //lấy ảnh ở trong thư viện điện thoại admin
                btn_add_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //lấy nội dung của máy admin
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        //setType("image") chỉ lấy ảnh
                        intent.setType("image/*");
                        startActivityForResult(intent,1);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edNameProType.getText().toString().isEmpty()){
                            tilNameProType.setError("Vui lòng nhập số tiền nạp!");
                            return;
                        }else{
                            tilNameProType.setErrorEnabled(false);
                        }
                        objProductType.setName_typePro(edNameProType.getText().toString().trim());
                        if(productTypeDao.insertProType(objProductType)>0){
                            Toast.makeText(getContext(), "Thêm thành công sản phẩm"+objProductType.getName_typePro(), Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getContext(), "Thêm ko thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });dialog.show();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Product_add.class);
                startActivity(intent);
            }
        });
        ryc_ProType.getId();
//        registerForContextMenu();
    }

    private void initView(View v) {
        btnMenuAdd = v.findViewById(R.id.btn_menu_add_pro);
        btnAddProType = v.findViewById(R.id.btn_Add_ProType);
        btnAddProduct = v.findViewById(R.id.btn_Add_Product);
        ryc_ProType = v.findViewById(R.id.ryc_ProType_manageProduct);
        ryc_Product = v.findViewById(R.id.ryc_Product_manageProduct);
        alphaFloatingAction = AnimationUtils.loadAnimation(getContext(),R.anim.floating_alpha);
        alphaFloatingAction2 = AnimationUtils.loadAnimation(getContext(),R.anim.floating_alpha2);

        productTypeDao = new ProductTypeDao(getContext());
        objProductType = new ProductType();
        product =  new Product();
        productDao = new ProductDao(getContext());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("rrrrrrr", "data: "+data);
        if(requestCode == 1 &&  resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri imgUri = data.getData();
            Log.d("zzzzzzz", "uri: "+imgUri);
            btn_add_image.setImageURI(imgUri);

            InputStream inputStream = null;
            try {
                inputStream = getContext().getContentResolver().openInputStream(imgUri);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer,0,bytesRead);
                }
                byte[] bytes = byteArrayOutputStream.toByteArray();
                objProductType.setImage_typePro(bytes);
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else {
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
        }
    }
    public  void loadDataProType(){
        listProType = productTypeDao.getAllProType();
        proTypeAdapter = new ProTypeAdapter(getContext(),listProType);
        ryc_ProType.setAdapter(proTypeAdapter);
    }
    public  void loadDataProduct(){
        listProduct = productDao.getAllProduct();
        productAdapter = new ProductAdapter(getContext(),listProduct);
        ryc_Product.setAdapter(productAdapter);
    }
    @Override
    public void onResume() {

        super.onResume();
        loadDataProType();
        loadDataProduct();
    }
}
