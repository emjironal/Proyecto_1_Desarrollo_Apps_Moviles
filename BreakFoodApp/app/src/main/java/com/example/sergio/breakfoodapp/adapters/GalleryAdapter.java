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
import com.example.sergio.breakfoodapp.ImageDownloader;
import com.example.sergio.breakfoodapp.ObjectSerializer;
import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.gestorfirebase.GestorImagenesFirebase;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{

    List<String> images;
    private Context context;
    private View view;
    Integer idrestaurant;


    public GalleryAdapter() {
    }

    public GalleryAdapter(List<String> images, Context context, int idrestaurant) {
        this.images = images;
        this.context = context;
        this.idrestaurant = idrestaurant;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_image_item, null, false);
        return new GalleryAdapter.GalleryViewHolder(this.view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder galleyViewHolder, int i) {
        String imagen = images.get(i);
        //TODO:
        GestorImagenesFirebase.getUrlFoto("restaurantes/"+idrestaurant.toString(),imagen).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)
            {
                ImageDownloader img = new ImageDownloader();
                try {
                    Bitmap bitmap = img.execute(uri.toString()).get();
                    galleyViewHolder.imageView.setImageBitmap(bitmap);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

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
