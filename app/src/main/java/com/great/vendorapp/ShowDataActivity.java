package com.great.vendorapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class ShowDataActivity extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ArrayList<ModelClass> arrayList;
    List<ModelClass> arrayListImages;
    ImageSlider imageSlider;

    SliderView sliderView;
    private MySliderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        recyclerView = findViewById(R.id.recyclerView);
        /*imageSlider = findViewById(R.id.imageSlider);*/
        sliderView=findViewById(R.id.sliderView);
        reference = FirebaseDatabase.getInstance().getReference("Uploads");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<>();
        arrayListImages = new ArrayList<>();
        init();
    }

    private void init() {
        clearAll();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelClass uploads = dataSnapshot.getValue(ModelClass.class);
                    uploads.setCategory(dataSnapshot.child("Category").getValue().toString());
                    arrayList.add(uploads);

                    /*for (int i = 0; i < arrayList.size(); i++) {
                        ModelClass list = arrayList.get(i);
                        String name = list.getImgLink0();
                        String name1 = list.getImgLink1();
                        String name2 = list.getImgLink2();
                        List<SlideModel> slideModels = new ArrayList<>();
                        slideModels.add(new SlideModel(name));
                        slideModels.add(new SlideModel(name1));
                        slideModels.add(new SlideModel(name2));
                        Toast.makeText(ShowDataActivity.this, slideModels.get(0).getImageUrl(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ShowDataActivity.this, slideModels.get(1).getImageUrl(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(ShowDataActivity.this, slideModels.get(2).getImageUrl(), Toast.LENGTH_SHORT).show();
                        imageSlider.setImageList(slideModels, true);
                    }*/

                }
                recyclerAdapter = new RecyclerAdapter(arrayList, ShowDataActivity.this);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();

                adapter = new MySliderAdapter(arrayList,ShowDataActivity.this);
                sliderView.setSliderAdapter(adapter);
                sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                sliderView.setIndicatorSelectedColor(Color.WHITE);
                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();
                adapter.notifyDataSetChanged();

                sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
                    @Override
                    public void onIndicatorClicked(int position) {
                        Log.i("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clearAll() {
        if (arrayList != null) {
            arrayList.clear();

            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        arrayList = new ArrayList<>();
    }


}
