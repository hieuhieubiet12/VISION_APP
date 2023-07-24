package com.example.vision_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vision_app.Adapter.ProductAdapter;
import com.example.vision_app.DAO.ProductDao;
import com.example.vision_app.Model.Product;

import java.util.ArrayList;

public class List_Product extends AppCompatActivity {
    private RecyclerView ryc_product;
    private ProductAdapter productAdapter;
    private ProductDao productDao;
    private ArrayList<Product> list;
    private ImageView btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        initView();
        loadData();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void loadData() {
//        Bundle b = getIntent().getExtras();
//        int id_proType = b.getInt("extra_idProductType");
//        Log.d("id_protype", "loadData: "+id_proType);
//        list = productDao.getAllProductById(id_proType);
//        Log.d("list", "loadData: "+list);
//        productAdapter = new ProductAdapter(List_Product.this,list);
//        ryc_product.setAdapter(productAdapter);
    }

    private void initView() {
        ryc_product = findViewById(R.id.ryc_Product_list);
        btn_back = findViewById(R.id.btn_toolbar_back);
        productDao = new ProductDao(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}