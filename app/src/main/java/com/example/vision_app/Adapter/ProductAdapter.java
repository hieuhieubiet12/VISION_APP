package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.Product;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.ProductDetail_Addmin;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context mContext;
    private ArrayList<Product> list;
    private Product product;

    public ProductAdapter(Context mContext, ArrayList<Product> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view_of_item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHolder(view_of_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        product = list.get(position);
        if(product != null && product.getImage()!= null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(product.getImage(),0, product.getImage().length);
            holder.img_item_imgProduct.setImageBitmap(bitmap);
        }
        holder.tv_item_nameProduct.setText(product.getName());
        holder.tv_item_Price.setText(String.valueOf(product.getPrice()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProductDetail_Addmin.class);
                Bundle b = new Bundle();
                b.putInt("extra_idProduct",product.getId());
                i.putExtras(b);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list.size()!=0){
            return list.size();
        }
        return 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_item_imgProduct;
        private TextView tv_item_nameProduct,tv_item_Price;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img_item_imgProduct = itemView.findViewById(R.id.img_item_imgProduct);
            tv_item_nameProduct = itemView.findViewById(R.id.tv_item_nameProduct);
            tv_item_Price = itemView.findViewById(R.id.tv_item_Price);
        }
    }
}
