package com.example.sergio.breakfoodapp.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sergio.breakfoodapp.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{

    List<String> images;
    private Context context;
    private View view;


    public GalleryAdapter() {
    }

    public GalleryAdapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_image_item, null, false);
        return new GalleryAdapter.GalleryViewHolder(this.view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder galleyViewHolder, int i) {
        //galleyViewHolder.imageView.setImageBitmap();
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class GalleryViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
        }
    }
}
