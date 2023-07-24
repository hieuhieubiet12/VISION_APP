package com.example.vision_app.Fragment.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vision_app.Adapter.ProductAdapter;
import com.example.vision_app.DAO.ProductDao;
import com.example.vision_app.Model.Product;
import com.example.vision_app.R;

import java.util.ArrayList;

public class NewProduct extends Fragment {
    private RecyclerView ryc_productNew;
    private ProductAdapter productAdapter;
    private ProductDao productDao;
    private ArrayList<Product> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_new_product,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        loadData();
    }

    private void loadData() {
        list = productDao.getAllProduct();
        productAdapter = new ProductAdapter(getContext(),list);
        ryc_productNew.setAdapter(productAdapter);
    }

    private void initView(View view) {
        ryc_productNew = view.findViewById(R.id.ryc_Product_new);
        productDao = new ProductDao(getContext());

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
