package com.example.sergio.breakfoodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sergio.breakfoodapp.adapters.RestaurantAdapter;
import com.example.sergio.breakfoodapp.http.GestorGetRequest;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.model.Restaurant;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResultListView extends AppCompatActivity {

    RecyclerView restaurantRecyclerView;
    RestaurantAdapter adapter;
    List<Restaurant> restaurantList;
    private MixpanelAPI mixpanelAPI;
    double longitude = 9.8560621;
    double latitude = -83.9112765;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list_view);
        restaurantRecyclerView = findViewById(R.id.restarant_recycler_view);
        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track("Result Activity");
        mixpanelAPI.flush();

        getResults();

        adapter = new RestaurantAdapter(restaurantList, this);
        restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        restaurantRecyclerView.setAdapter(adapter);
    }

    private void getResults(){
        Intent intent = getIntent();
        String nombreRestaurante = intent.getStringExtra("nombreRestaurante");
        String calificacion = intent.getStringExtra("calificacion").toLowerCase();
        String distancia = intent.getStringExtra("distancia").toLowerCase();
        String precio = intent.getStringExtra("precio").toLowerCase();
        String tipoComida = intent.getStringExtra("tipo");
        latitude = intent.getDoubleExtra("latitude",latitude);
        longitude = intent.getDoubleExtra("longitude",longitude);


        String url = "https://appetyte.herokuapp.com/android/buscarRestaurante";
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if(!nombreRestaurante.equals(""))
            nameValuePairs.add(new BasicNameValuePair("name", nombreRestaurante));
        if(!precio.equals("cualquiera"))
            nameValuePairs.add(new BasicNameValuePair("price", precio));
        if(!calificacion.equals("cualquiera"))
            nameValuePairs.add(new BasicNameValuePair("score", calificacion));
        if(!distancia.equals("cualquiera")){
            nameValuePairs.add(new BasicNameValuePair("distance", distancia));
            nameValuePairs.add(new BasicNameValuePair("latitudepos", Double.toString(latitude)));
            nameValuePairs.add(new BasicNameValuePair("longitudepos", Double.toString(longitude)));
        }
        if(!tipoComida.toLowerCase().equals("cualquiera"))
            nameValuePairs.add(new BasicNameValuePair("footype", tipoComida));

        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url,nameValuePairs));
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
                r1.setScore(restaurant.getDouble("realscore"));
                restaurantList.add(r1);
            }
        }catch (Exception ignored){}
    }

    public void search(View view){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(getApplicationContext(), BuscadorActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }


    public void addNewRestaurant(View view) {

        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(getApplicationContext(), AgregarRestauranteActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);

    }
}
