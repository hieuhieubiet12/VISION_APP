package com.example.vision_app.Fragment.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vision_app.DAO.ProductDao;
import com.example.vision_app.DAO.ProductTypeDao;
import com.example.vision_app.Model.Product;
import com.example.vision_app.R;


public class Fragment_productDetail extends Fragment {
    private ImageView img_productDetail;
    private TextView tv_name,tv_price,tv_id,tv_discribe,tv_price_productDetail;
    private RadioButton rdo_default,rdo_4month;
    private ProductDao dao;
    private Product product;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        Bundle b = null;
        if(getActivity()!=null){
            b = getActivity().getIntent().getExtras();
        }
        int id = b.getInt("extra_idProduct");
        //get product by id_product
        product = dao.getProductByIdProduct(id);
        setDataForUI();
        rdo_4month.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_price.setText(String.valueOf(product.getPrice()+(product.getPrice()*0.1)));
                }
            }
        });
        rdo_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tv_price.setText(String.valueOf(product.getPrice()));
                }
            }
        });

    }

    private void initView(View view) {
        img_productDetail = view.findViewById(R.id.img_productDetail);
        tv_name = view.findViewById(R.id.name_productDetail);
        tv_price = view.findViewById(R.id.tv_price_productDetail);
        tv_id = view.findViewById(R.id.tv_id_productDetail);
        tv_discribe = view.findViewById(R.id.tv_discribe_productDetail);
        rdo_default = view.findViewById(R.id.rdo_baohanh0);
        rdo_4month = view.findViewById(R.id.rdo_baohanh1);
        tv_price_productDetail = view.findViewById(R.id.tv_price_productDetail);
        dao = new ProductDao(getContext());
        product = new Product();
    }

    public void setDataForUI(){
        if(product != null && product.getImage()!= null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length);
            img_productDetail.setImageBitmap(bitmap);
        }
        tv_name.setText(product.getName());
        tv_price.setText(String.valueOf(product.getPrice()));
        tv_id.setText(String.valueOf(product.getId()));
        tv_discribe.setText(product.getDescription());
    }
}