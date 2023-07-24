package com.example.vision_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.vision_app.DAO.ProductDao;
import com.example.vision_app.DAO.UserDao;
import com.example.vision_app.Model.Product;

public class ProductDetail extends AppCompatActivity {
    private ImageView btn_back,btn_favourite,btn_cart;
    private TextView tv_title,tv_btn_buyNow,tv_btn_addCart;
    private CardView btn_buyNow,btn_addCart;
    private Product product;
    private ProductDao productDao;
    private UserDao userDao;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initView();
        SharedPreferences preferences = getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName","");
        int role = userDao.getUserByUserName(userName).getRole();

        if(role == 0){
            btn_cart.setVisibility(View.INVISIBLE);
            btn_favourite.setVisibility(View.INVISIBLE);
            tv_btn_buyNow.setText("Xóa sp");
            tv_btn_addCart.setText("Sửa sản phẩm");
            tv_btn_buyNow.setTextColor(R.color.white);

        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle b = getIntent().getExtras();
        int id_product = b.getInt("extra_idProduct");
        tv_title.setText(productDao.getProductByIdProduct(id_product).getName());

    }

    private void initView() {
        tv_title = findViewById(R.id.tv_toolbar_productDetail);
        tv_btn_addCart = findViewById(R.id.tv_btn_addCart);
        tv_btn_buyNow = findViewById(R.id.tv_btn_buyNow);
        btn_back = findViewById(R.id.btn_back_addProduct);
        btn_favourite = findViewById(R.id.btn_product_favorite_productDetail);
        btn_cart = findViewById(R.id.btn_cart_productDetail);
        btn_addCart = findViewById(R.id.btn_Add_cart);
        btn_buyNow = findViewById(R.id.btn_buyNow);

        product = new Product();
        productDao = new ProductDao(getApplicationContext());
        userDao = new UserDao(getApplicationContext());
    }
}