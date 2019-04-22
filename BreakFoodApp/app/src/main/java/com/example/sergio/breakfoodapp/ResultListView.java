package com.example.sergio.breakfoodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sergio.breakfoodapp.adapters.RestaurantAdapter;
import com.example.sergio.breakfoodapp.http.GestorGetRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultListView extends AppCompatActivity {

    RecyclerView restaurantRecyclerView;
    RestaurantAdapter adapter;
    List<Restaurant> restaurantList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list_view);

        restaurantRecyclerView = findViewById(R.id.restarant_recycler_view);

        String url = "https://appetyte.herokuapp.com/android/getRestaurantes";
        String result = LectorHttpResponse.leer(GestorGetRequest.getData(url));
        JSONArray jsonArray = new JSONArray();
        restaurantList = new ArrayList<>();
        try{
            jsonArray = new JSONArray(result);
            Restaurant r1;
            for (int i = 0; i < jsonArray.length(); i++) {
                r1 = new Restaurant();
                JSONObject restaurant = jsonArray.getJSONObject(i);
                r1.setId(restaurant.getInt("idrestaurant"));
                r1.setName(restaurant.getString("name"));
                r1.setLat(restaurant.getDouble("latitudepos"));
                r1.setLongitude(restaurant.getDouble("longitudepos"));
                r1.setPrice(restaurant.getString("price"));
                r1.setOpen(restaurant.getString("open"));
                r1.setClose(restaurant.getString("close"));
                r1.setFoodType(restaurant.getString("foodtype"));
                restaurantList.add(r1);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        adapter = new RestaurantAdapter(restaurantList, this);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        restaurantRecyclerView.setAdapter(adapter);
    }



    public void search(View view){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(getApplicationContext(), BuscadorActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }


}