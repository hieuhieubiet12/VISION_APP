package com.example.vision_app.Fragment.User;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.vision_app.Adapter.SliderCourseAdapter;
import com.example.vision_app.Model.PhotoSliderCourse;
import com.example.vision_app.R;

import java.util.ArrayList;

public class FragmentSlideShow extends Fragment {
    ViewPager2 viewPager2;

    ArrayList<PhotoSliderCourse> listPhotos;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int currentItems = viewPager2.getCurrentItem();
            if(currentItems == listPhotos.size() - 1){
                viewPager2.setCurrentItem(0);
            }else {
                viewPager2.setCurrentItem(currentItems + 1);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_slide_show, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.sliderImg);

        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        CompositePageTransformer transformer = new CompositePageTransformer();
//        transformer.addTransformer(new MarginPageTransformer(40));
//        transformer.addTransformer(new ViewPager2.PageTransformer() {
//            @Override
//            public void transformPage(@NonNull View page, float position) {
//                float r = 1 - Math.abs(position);
//                page.setScaleY(0.85f + r* 0.15f);
//            }
//        });
        viewPager2.setPageTransformer(transformer);

        listPhotos = getListPhotos();

        SliderCourseAdapter photoAdapter = new SliderCourseAdapter(listPhotos);
        viewPager2.setAdapter(photoAdapter);



        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,2500);
            }
        });
    }
    public ArrayList<PhotoSliderCourse> getListPhotos (){
        ArrayList<PhotoSliderCourse> listPhotos = new ArrayList<>();
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_01_optimized));
        listPhotos.add(new PhotoSliderCourse(R.drawable.img_02_optimized));
        return listPhotos;
    }
}