package com.example.sergio.breakfoodapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sergio.breakfoodapp.BitmapManager;
import com.example.sergio.breakfoodapp.GestorImagenes;
import com.example.sergio.breakfoodapp.ObjectSerializer;
import com.example.sergio.breakfoodapp.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
        String imagen = images.get(i);
        //TODO: 
        Uri imageURI = GestorImagenes.getInstance().getUriFoto("14","gege").getResult();
        Bitmap bitmap = getImageBitmap(imagen);
        galleyViewHolder.imageView.setImageBitmap(bitmap);
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

    public Bitmap getImageBitmap(String encodedImage)
    {
        try
        {
            File file = (File) ObjectSerializer.deserialize(encodedImage);
            FileInputStream imageStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(imageStream);
            return BitmapFactory.decodeStream(bufferedInputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Bitmap getBitmap(String encodedImage)
    {
        Bitmap bitmap = null;
        try {
            ArrayList<String> strings= (ArrayList<String>) ObjectSerializer.deserialize(encodedImage);
            byte[] bytes = BitmapManager.stringsToByte(strings);
            bitmap = BitmapManager.byteArrayToBitmap(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
