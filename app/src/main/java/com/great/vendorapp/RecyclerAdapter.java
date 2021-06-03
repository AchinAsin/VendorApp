package com.great.vendorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    ArrayList<ModelClass> arrayList = new ArrayList<>();
    Context context;
    public RecyclerAdapter(ArrayList<ModelClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ModelClass pos = arrayList.get(position);
        holder.textViewProductName.setText("Product Name: "+pos.getProductName());
        holder.textViewCategory.setText("Category: "+pos.getCategory());
        holder.textViewPriceAmount.setText("Price: "+pos.getPriceAmount()+"/-");
        holder.textViewOffer.setText("Offer: "+pos.getOffer()+"%");
        holder.textViewDeliveryCharges.setText("Delivery: "+pos.getDeliveryCharges()+"$");
        holder.textViewGST.setText("GST: "+pos.getGST()+"%");
        /*Glide.with(context).load(pos.getImageUrl()).into(holder.imageView);*/
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCategory, textViewDeliveryCharges, textViewGST, textViewOffer, textViewPriceAmount, textViewProductName;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.textView_Category);
            textViewDeliveryCharges = itemView.findViewById(R.id.textView_DeliveryCharges);
            textViewGST = itemView.findViewById(R.id.textView_GST);
            textViewOffer = itemView.findViewById(R.id.textView_Offer);
            textViewPriceAmount = itemView.findViewById(R.id.textView_PriceAmount);
            textViewProductName = itemView.findViewById(R.id.textView_ProductName);
            /*imageView=itemView.findViewById(R.id.imageView);*/
        }
    }
}
