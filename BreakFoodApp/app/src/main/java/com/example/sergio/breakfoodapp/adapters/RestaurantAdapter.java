package com.example.sergio.breakfoodapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {




    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        ImageView localImage;
        TextView name, distance, contactText, expense;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
