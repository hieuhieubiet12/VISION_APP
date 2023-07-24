package com.example.vision_app.Fragment.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vision_app.Adapter.ProTypeAdapter;
import com.example.vision_app.Adapter.ProductAdapter;
import com.example.vision_app.DAO.ProductDao;
import com.example.vision_app.DAO.ProductTypeDao;
import com.example.vision_app.Model.Product;
import com.example.vision_app.Model.ProductType;
import com.example.vision_app.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private RecyclerView ryc_proType;
    private ProTypeAdapter proTypeAdapter;
    private ProductTypeDao typeDao;
    private ArrayList<ProductType> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ryc_proType = view.findViewById(R.id.ryc_Product);
        typeDao = new ProductTypeDao(getContext());
        loadData();
    }
    private void loadData() {
        list = typeDao.getAllProType();
        proTypeAdapter = new ProTypeAdapter(getContext(),list);
        ryc_proType.setAdapter(proTypeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
