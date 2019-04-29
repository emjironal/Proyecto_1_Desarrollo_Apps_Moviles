package com.example.sergio.breakfoodapp.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.BitmapManager;
import com.example.sergio.breakfoodapp.Controller;
import com.example.sergio.breakfoodapp.GestorImagenes;
import com.example.sergio.breakfoodapp.ObjectSerializer;
import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.gestorfirebase.GestorImagenesFirebase;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.UploadTask;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AgregarImagenRestaurante extends AppCompatActivity
{
    private static final int PICK_IMAGE = 100;
    private ImageView imgVistaPrevia;
    private ArrayList<Bitmap> imagenes;
    private ArrayList<String> stringAdapter;
    private ArrayAdapter<String> adapter;
    private Integer idrestaurant;
    private ArrayList<Uri> files;
    private MixpanelAPI mixpanelAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_imagen_restaurante);
        idrestaurant = getIntent().getIntExtra("idrestaurant",0);

        imgVistaPrevia = findViewById(R.id.imgVistaPrevia);
        imagenes = new ArrayList<>();
        stringAdapter = new ArrayList<>();
        files = new ArrayList<>();

        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();

        ListView listView = findViewById(R.id.listViewImagenes);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stringAdapter);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imgVistaPrevia.setImageURI(files.get((position)));
            }
        });
    }

    public void btnConfirmarSubidaListener(View view)
    {
        String url = "https://appetyte.herokuapp.com/android/agregarImagenes";
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("idrestaurant", idrestaurant.toString()));
        for(int i = 0; i < files.size(); i++)
        {
            GestorImagenesFirebase.uploadLocalImage(this, "restaurantes/" + idrestaurant.toString(), files.get(i));
            agregarImagenDB(files.get(i).getLastPathSegment());
        }
        HttpResponse response = GestorPostRequest.postData(url, nameValuePairs);
        String resultStr = LectorHttpResponse.leer(response);
        try
        {
            JSONObject jsonObject = new JSONObject(resultStr);
            boolean result = jsonObject.getBoolean("result");
            if(result)
            {
                Toast.makeText(this, "Las imágenes se agregaron", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(this, "Las imágenes no se agregaron", Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e)
        {
            Log.e("Error", e.getMessage());
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    public void btnAgregaImagenListener(View view)
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            Uri imageUri = data.getData();
            if(imageUri != null)
            {
                Picasso.with(getApplicationContext()).load(imageUri).into(imgVistaPrevia);
                files.add(imageUri);
                stringAdapter.add(imageUri.getLastPathSegment()); //obtiene el nombre de la imagen y lo agrega
                adapter.notifyDataSetChanged(); //actualiza el list view
            }
        }
    }

    public void agregarImagenDB(String fileNameWithExtension)
    {
        String url = "https://appetyte.herokuapp.com/android/agregarImagenes";
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("idrestaurant", idrestaurant.toString()));
        nameValuePairs.add(new BasicNameValuePair("pictures", fileNameWithExtension));
        HttpResponse response = GestorPostRequest.postData(url, nameValuePairs);
        String resultStr = LectorHttpResponse.leer(response);
        try
        {
            JSONObject jsonObject = new JSONObject(resultStr);
            boolean result = jsonObject.getBoolean("result");
            if(result)
            {
                Toast.makeText(AgregarImagenRestaurante.this, "Las imágenes se agregaron", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(AgregarImagenRestaurante.this, "Las imágenes no se agregaron", Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e)
        {
            Log.e("Error", e.getMessage());
            Toast.makeText(AgregarImagenRestaurante.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
