package com.example.vision_app.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vision_app.DAO.CartDao;
import com.example.vision_app.Model.CartItem;
import com.example.vision_app.Model.Product;
import com.example.vision_app.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItems;
    private Context context;

    public CartAdapter(ArrayList<CartItem> cartItems,Context context) {
        this.cartItems = cartItems;
        this.context=context;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        RadioButton rdoCartShop;
        ImageView ivProductImage;
        TextView tvProductName;
        TextView tvProductPrice;
        ImageButton btnDowSlAdd;
        TextView tvSl;
        ImageButton btnUpSlAdd;
        ImageButton itemDeleteListCart;

        public CartViewHolder(View itemView) {
            super(itemView);
            rdoCartShop = itemView.findViewById(R.id.rdo_cart_shop);
            ivProductImage = itemView.findViewById(R.id.item_image_list_cart);
            tvProductName = itemView.findViewById(R.id.item_name_product_list_cart);
            tvProductPrice = itemView.findViewById(R.id.item_price_list_cart);
            btnDowSlAdd = itemView.findViewById(R.id.btn_dowSL_add);
            tvSl = itemView.findViewById(R.id.tv_Sl);
            btnUpSlAdd = itemView.findViewById(R.id.btn_upSL_add);
            itemDeleteListCart = itemView.findViewById(R.id.item_delete_list_cart);
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder,  int position) {
        CartItem cartItem = cartItems.get(position);
        SharedPreferences preferences = context.getSharedPreferences("remember", Context.MODE_PRIVATE);
        String userName = preferences.getString("userName","");

        // Hiển thị tên sản phẩm và giá tiền
        holder.tvProductName.setText(cartItem.getProductName());
        holder.tvProductPrice.setText(String.valueOf(cartItem.getProductPrice()));

        // Hiển thị số lượng sản phẩm
        holder.tvSl.setText(String.valueOf(cartItem.getQuantity()));

        // Chuyển đổi mảng byte thành Bitmap và hiển thị ảnh sản phẩm
        byte[] imageBytes = cartItem.getProductImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.ivProductImage.setImageBitmap(bitmap);
        holder.itemDeleteListCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo hộp thoại xác nhận xóa sản phẩm
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm "+cartItem.getProductName()+" khỏi giỏ hàng?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xóa sản phẩm khỏi giỏ hàng bằng cách gọi phương thức removeFromCart trong CartDao
                        int id_cart = cartItem.getId_cart();
                        CartDao cartDao = new CartDao(context, "username"); // Thay "username" bằng tên người dùng thực tế
                        cartDao.removeFromCartById(id_cart,userName);

                        // Sau khi xóa thành công, cập nhật lại danh sách sản phẩm trong giỏ hàng và cập nhật RecyclerView
                        cartItems.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hủy", null);
                builder.show();
            }
        });

        // Xử lý sự kiện khi nhấn nút tăng số lượng
        holder.btnUpSlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cartItem.getQuantity();
                cartItem.setQuantity(currentQuantity + 1);
                notifyDataSetChanged();
            }
        });

        // Xử lý sự kiện khi nhấn nút giảm số lượng
        holder.btnDowSlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = cartItem.getQuantity();
                if (currentQuantity > 1) {
                    cartItem.setQuantity(currentQuantity - 1);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }
}


