package com.example.sergio.breakfoodapp.restaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.adapters.GalleryAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantGalleryActivity extends AppCompatActivity {

    int idrestaurant;
    GalleryAdapter adapter;
    List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_gallery);

        idrestaurant = getIntent().getIntExtra("idrestaurant",0);
        String restName = getIntent().getStringExtra("restName");
        TextView name = findViewById(R.id.gallery_rest_name);
        name.setText(restName);

        //TODO: obtener imagenes de la bd


        
        images = new ArrayList<>();
        adapter = new GalleryAdapter(images, getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
