package com.example.vision_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vision_app.Model.ProductType;
import com.example.vision_app.R;

import java.util.ArrayList;

public class SpinnerProTypeAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ProductType> mList;

    public SpinnerProTypeAdapter(Context mContext, ArrayList<ProductType> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewOfItem viewOfItem = null;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_item_spinner, null);
            viewOfItem = new ViewOfItem();
            viewOfItem.tvName = convertView.findViewById(R.id.tvName);
            convertView.setTag(viewOfItem);

        }else{
            viewOfItem = (ViewOfItem) convertView.getTag();
        }
        viewOfItem.tvName.setText(mList.get(position).getName_typePro());
        return convertView;
    }

    public static class ViewOfItem{
        TextView tvName;

    }
}
