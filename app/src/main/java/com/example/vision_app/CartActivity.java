package com.example.vision_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.vision_app.Adapter.CartAdapter;
import com.example.vision_app.DAO.CartDao;
import com.example.vision_app.Helper.DBhelper;
import com.example.vision_app.Model.CartItem;
import com.example.vision_app.Model.Product;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<Product> cartItemList;
    private SQLiteDatabase database;
    private ImageView btnback;
    private String userName;
    private CartDao cartDao;
    private ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycler_cart_shop);
        btnback=findViewById(R.id.list_cart_back);
        SharedPreferences preferences = getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName", "");
//
//        // Tạo đối tượng CartDao để quản lý giỏ hàng của người dùng hiện tại
//        CartDao cartDao = new CartDao(database, userName);
//
//        // Lấy danh sách sản phẩm trong giỏ hàng của người dùng hiện tại
//        cartItemList = cartDao.getCartItems();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,ProductDetail.class));
            }
        });
//        cartItemList = new ArrayList<>();
//
//        DBhelper dbHelper = new DBhelper(this);
//        database = dbHelper.getReadableDatabase();
//        CartDao cartDao = new CartDao(database);
//
//        cartItemList = cartDao.getCartItems();
//
//        cartAdapter = new CartAdapter(this, cartItemList, this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(cartAdapter);
//    }
//
//    @Override
//    public void onItemDeleteClick(int productId) {
//        // Xóa sản phẩm khỏi giỏ hàng của người dùng hiện tại
//        cartDao.removeFromCart(productId);
//
//        // Cập nhật lại danh sách sản phẩm trong giỏ hàng và cập nhật RecyclerView
//        cartItemList = cartDao.getCartItems();
//        cartAdapter.notifyDataSetChanged();
//
//
//    }
//    @Override
//    public void onItemQuantityIncreaseClick(int productId) {
//        CartDao cartDao = new CartDao(database);
//        cartDao.increaseCartItemQuantity(productId);
//        updateCartItemList();
//    }
//
//    @Override
//    public void onItemQuantityDecreaseClick(int productId) {
//        CartDao cartDao = new CartDao(database);
//        cartDao.decreaseCartItemQuantity(productId);
//        updateCartItemList();
//    }
//
//    private void updateCartItemList() {
//        CartDao cartDao = new CartDao(database);
//        cartItemList.clear();
//        cartItemList.addAll(cartDao.getCartItems());
//        cartAdapter.notifyDataSetChanged();
//    }
//        DBhelper databaseHelper = new DBhelper(this);
//        cartDao = new CartDao(getApplicationContext(), "username"); // Thay "username" bằng tên người dùng thực tế
//        cartItems = cartDao.getCartItems();

        // Khởi tạo RecyclerView và đặt Adapter
        recyclerView = findViewById(R.id.recycler_cart_shop);
        cartDao = new CartDao(getApplicationContext(), userName);
        cartItems = cartDao.getCartItems();

        // Khởi tạo RecyclerView và đặt Adapter
        cartAdapter = new CartAdapter(cartItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);


        // Khởi tạo nút thanh toán
        Button btnCheckout = findViewById(R.id.btn_checkout);
        btnCheckout.setOnClickListener(v -> {
            // Xử lý khi người dùng nhấn nút thanh toán
            // Viết mã xử lý thanh toán ở đây
        });
    }

    // Cập nhật giỏ hàng sau khi thực hiện thanh toán hoặc xóa sản phẩm
    private void updateCart() {
        cartItems = cartDao.getCartItems();
        cartAdapter.notifyDataSetChanged();
    }
    }

//    @Override
//    public void onItemDeleteClick(int productId) {
//        // TODO: Implement logic to remove item from cart
//        // Ví dụ:
//        // CartDao cartDao = new CartDao(database);
//        // cartDao.removeFromCart(productId);
//
//        // Sau khi xóa thành công, cập nhật lại danh sách sản phẩm trong giỏ hàng và cập nhật RecyclerView
//        cartItemList = cartDao.getCartItems();
//        cartAdapter.setCartItemList(cartItemList);
//        cartAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onItemQuantityIncreaseClick(int productId) {
//        // TODO: Implement logic to increase quantity of a cart item
//        // Ví dụ:
//        // CartDao cartDao = new CartDao(database);
//        // cartDao.increaseCartItemQuantity(productId);
//
//        // Sau khi tăng số lượng thành công, cập nhật lại danh sách sản phẩm trong giỏ hàng và cập nhật RecyclerView
//        cartItemList = cartDao.getCartItems();
//        cartAdapter.setCartItemList(cartItemList);
//        cartAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onItemQuantityDecreaseClick(int productId) {
//        // TODO: Implement logic to decrease quantity of a cart item
//        // Ví dụ:
//        // CartDao cartDao = new CartDao(database);
//        // cartDao.decreaseCartItemQuantity(productId);
//
//        // Sau khi giảm số lượng thành công, cập nhật lại danh sách sản phẩm trong giỏ hàng và cập nhật RecyclerView
//        cartItemList = cartDao.getCartItems();
//        cartAdapter.setCartItemList(cartItemList);
//        cartAdapter.notifyDataSetChanged();
//    }



