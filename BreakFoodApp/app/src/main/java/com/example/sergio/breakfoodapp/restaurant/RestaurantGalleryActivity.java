package com.example.sergio.breakfoodapp.restaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.ObjectSerializer;
import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.adapters.GalleryAdapter;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RestaurantGalleryActivity extends AppCompatActivity {

    int idrestaurant;
    GalleryAdapter adapter;
    List<String> images;
    private MixpanelAPI mixpanelAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_gallery);

        idrestaurant = getIntent().getIntExtra("idrestaurant",0);
        String restName = getIntent().getStringExtra("restName");
        TextView name = findViewById(R.id.gallery_rest_name);
        name.setText(restName);

        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();


        String url = "https://appetyte.herokuapp.com/android/getImagenes";
        List<NameValuePair> restaurantl = new ArrayList<>();
        restaurantl.add(new BasicNameValuePair("idrestaurant", ""+idrestaurant));

        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, restaurantl));
        images = new ArrayList<>();

        JSONArray jsonArray = new JSONArray();
        try{
            jsonArray = new JSONArray(result);
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


    public void addImages(View view){
        Intent i = new Intent(getApplicationContext(), AgregarImagenRestaurante.class);
        i.putExtra("idrestaurant", idrestaurant);
        startActivity(i);
    }
}
