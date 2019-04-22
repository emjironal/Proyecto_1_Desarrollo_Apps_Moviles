package com.example.sergio.breakfoodapp;

import android.content.Entity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.model.Restaurant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectedRestaurantActivity extends AppCompatActivity {


    int idrestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_restaurant);

        idrestaurant = getIntent().getIntExtra("idrestaurant",1);

        String url = "https://appetyte.herokuapp.com/android/getRestaurante";
        List<NameValuePair> restaurantl = new ArrayList<>();
        restaurantl.add(new BasicNameValuePair("idrestaurant", ""+idrestaurant));

        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, restaurantl));

        JSONArray jsonArray = new JSONArray();
        Restaurant r1 = new Restaurant();
        try{
            jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                r1 = new Restaurant();
                JSONObject restaurant = jsonArray.getJSONObject(i);
                r1.setName(restaurant.getString("name"));
                r1.setLat(restaurant.getDouble("latitudepos"));
                r1.setLongitude(restaurant.getDouble("longitudepos"));
                r1.setPrice(restaurant.getString("price"));
                r1.setOpen(restaurant.getString("open"));
                r1.setClose(restaurant.getString("close"));
                r1.setFoodType(restaurant.getString("foodtype"));
                r1.setScore(restaurant.getDouble("realscore"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        TextView name = findViewById(R.id.restaurant_profile_name);
        TextView schedule = findViewById(R.id.restaurant_profile_schedule);
        TextView foodType = findViewById(R.id.restaurant_profile_foodType);
        TextView priceRange = findViewById(R.id.restaurant_profile_price_point);

        name.setText(r1.getName());
        schedule.setText(String.format("%s - %s",r1.getOpen(), r1.getClose()));
        foodType.setText(r1.getFoodType());
        priceRange.setText(r1.getPrice());


        //TODO: cargar puntaje

    }

    public void getAllCommments(View view) {
        //TODO: abre activity con todos los comentarios


    }



}
