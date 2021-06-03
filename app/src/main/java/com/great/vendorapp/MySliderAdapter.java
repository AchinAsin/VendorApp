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
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;
public class MySliderAdapter extends SliderViewAdapter<MySliderAdapter.SliderAdapterVH> {

    private List<ModelClass> mSliderItems = new ArrayList<>();
    private Context context;

    public MySliderAdapter(List<ModelClass> mSliderItems, Context context) {
        this.mSliderItems = mSliderItems;
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.image_slider_layout_item,null);
        return new SliderAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        ModelClass sliderItem = mSliderItems.get(position);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgLink0())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgLink1())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImgLink2())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }


    static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        ImageView imageViewBackground;
        ImageView imageGifContainer;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
        }
    }
}
