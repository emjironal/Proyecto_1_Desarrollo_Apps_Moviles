package com.example.sergio.breakfoodapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.SelectedRestaurantActivity;
import com.example.sergio.breakfoodapp.model.DistanceManager;
import com.example.sergio.breakfoodapp.model.Restaurant;

import java.util.List;
import java.util.zip.Inflater;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    List<Restaurant> restaurantList;
    private Context context;
    private View view;


    public RestaurantAdapter(){}

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context){
        this.restaurantList = restaurantList;
        this.context = context;
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_item, null, false);
        return new RestaurantViewHolder(this.view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int i) {
        final Restaurant currentRestaurant = restaurantList.get(i);
        restaurantViewHolder.name.setText(currentRestaurant.getName());
        restaurantViewHolder.expense.setText("$");

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        String pricePoint =  "";
        switch (currentRestaurant.getPrice()){
            case "barato":{
                pricePoint = "$";
                restaurantViewHolder.expense.setTextColor(Color.parseColor("#2fba1f"));
                break;
            }
            case "caro":{
                pricePoint = "$$$";
                restaurantViewHolder.expense.setTextColor(Color.parseColor("#ba1e1e"));
                break;
            }
            case "medio":{
                pricePoint = "$$";
                restaurantViewHolder.expense.setTextColor(Color.parseColor("#ba831d"));
                break;
            }
            default:
                pricePoint = "undefined";
        }
        restaurantViewHolder.expense.setText(pricePoint);
        restaurantViewHolder.contactText.setText(String.format("%s - %s",currentRestaurant.getOpen(), currentRestaurant.getClose()));
        restaurantViewHolder.distance.setText(currentRestaurant.getFoodType());
        restaurantViewHolder.localImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SelectedRestaurantActivity.class);
                i.putExtra("idrestaurant", currentRestaurant.getId());
                context.startActivity(i);
            }
        });

        double distance = DistanceManager.distance(latitude, longitude, currentRestaurant.getLat(), currentRestaurant.getLongitude());

        //restaurantViewHolder.distance.setText(String.format("%d,02%d KM",distance/1000, distance%1000));
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{

        ImageView localImage;
        TextView name, distance, contactText, expense;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            localImage = itemView.findViewById(R.id.restaurant_item_image);
            name = itemView.findViewById(R.id.restaurant_item_name);
            distance = itemView.findViewById(R.id.restaurant_item_distance);
            contactText = itemView.findViewById(R.id.restaurant_item_contact);
            expense = itemView.findViewById(R.id.restaurant_item_price_range);

        }
    }


}
