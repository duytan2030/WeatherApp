package com.example.baocaoweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopAddater1 extends RecyclerView.Adapter<ShopAddater1.ViewHolder> {
    Context context;
    ArrayList<ThoiTiet> listThoiTiet;

    public ShopAddater1(Context context, ArrayList<ThoiTiet> obj) {
        this.context = context;
        this.listThoiTiet = obj;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_row2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ThoiTiet thoiTiet = listThoiTiet.get(i);
        holder.tv_ngay.setText(thoiTiet.getNgay());
        holder.tv_min.setText(listThoiTiet.get(i).getMinTemp());
        holder.tv_max.setText(listThoiTiet.get(i).getMaxTemp());
        Picasso.with(context).load("https://openweathermap.org/img/wn/" + listThoiTiet.get(i).image + "@2x.png").into(holder.img_status);


    }

    @Override
    public int getItemCount() {
        return listThoiTiet.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_min, tv_max, tv_ngay;
        ImageView img_status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_max = (TextView) itemView.findViewById(R.id.textview_maxLV);
            tv_min = (TextView) itemView.findViewById(R.id.textview_minLV);
            tv_ngay = (TextView) itemView.findViewById(R.id.textview_ngayListView);
            img_status = (ImageView) itemView.findViewById(R.id.image_iconLV);
        }
    }
}
