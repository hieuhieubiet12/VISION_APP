package thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.DAO.ProductTypeDao;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.Model.ProductType;
import thiennhph18697.fpt.poly.md18202_pro1121_p301_ca1_vison.R;

public class ProTypeAdapter extends RecyclerView.Adapter<ProTypeAdapter.ProTypeViewHolder> {
    private Context context;
    private ArrayList<ProductType> list;
    private ProductType productType;

    private ProductTypeDao typeDao;

    public ProTypeAdapter(Context context, ArrayList<ProductType> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produc_type,parent,false);
        return new ProTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProTypeViewHolder holder, int position) {
        productType = list.get(position);
        if (productType != null && productType.getImage_typePro() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(productType.getImage_typePro(), 0, productType.getImage_typePro().length);
            holder.img_loai_sp.setImageBitmap(bitmap);
        }

        holder.tvName_loaisp.setText(productType.getName_typePro());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
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

    class ProTypeViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_loai_sp;
        private TextView tvName_loaisp;
        public ProTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            img_loai_sp = itemView.findViewById(R.id.img_loai_sp);
            tvName_loaisp = itemView.findViewById(R.id.tvName_loaisp);
        }
    }
}
