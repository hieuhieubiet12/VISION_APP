package com.example.vision_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.vision_app.Adapter.ViewPagerAdapter;
import com.example.vision_app.DAO.UserDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private UserDao dao;
    private ViewPager viewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.view_pager);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        dao = new UserDao(getApplicationContext());

        SharedPreferences preferences = getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName","");
        int role = dao.getUserByUserName(userName).getRole();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.getRole(role);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        if (role == 0){
                            bottomNavigationView.getMenu().findItem(R.id.action_home).setTitle("QLy Sản phẩm");
                            bottomNavigationView.getMenu().findItem(R.id.action_home).setIcon(R.drawable.baseline_list_alt_24);
                        }
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_new_product).setChecked(true);
                        if (role == 0){
                            bottomNavigationView.getMenu().findItem(R.id.action_new_product).setTitle("QL Đơn hàng");
                            bottomNavigationView.getMenu().findItem(R.id.action_new_product).setIcon(R.drawable.baseline_list_alt_24);
                        }
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_share).setChecked(true);
                        if (role == 0){
                            bottomNavigationView.getMenu().findItem(R.id.action_share).setTitle("Nạp rút");
                            bottomNavigationView.getMenu().findItem(R.id.action_share).setIcon(R.drawable.baseline_list_alt_24);
                        }
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.action_person).setChecked(true);
                        if (role == 0){

                            bottomNavigationView.getMenu().findItem(R.id.action_person).setTitle("MyAdmin");
                            bottomNavigationView.getMenu().findItem(R.id.action_person).setIcon(R.drawable.baseline_person_menu);
                        }
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.action_home ){
                    viewPager.setCurrentItem(0);
                } else if (item.getItemId()==R.id.action_new_product) {
                    viewPager.setCurrentItem(1);
                } else if (item.getItemId()==R.id.action_share) {
                    viewPager.setCurrentItem(2);
                } else if (item.getItemId()==R.id.action_person) {
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });
    }
}