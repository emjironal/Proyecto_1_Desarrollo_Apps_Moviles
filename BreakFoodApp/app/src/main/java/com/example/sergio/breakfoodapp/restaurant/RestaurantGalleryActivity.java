package com.example.sergio.breakfoodapp.restaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.adapters.GalleryAdapter;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

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


        String url = "https://appetyte.herokuapp.com/android/getImagenes";
        List<NameValuePair> restaurantl = new ArrayList<>();
        restaurantl.add(new BasicNameValuePair("idrestaurant", ""+idrestaurant));

        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, restaurantl));
        images = new ArrayList<>();

        JSONArray jsonArray = new JSONArray();
        try{
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                images.add(jsonObject.getString("picture"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        adapter = new GalleryAdapter(images, getApplicationContext());

        RecyclerView recyclerView = findViewById(R.id.gallery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}
