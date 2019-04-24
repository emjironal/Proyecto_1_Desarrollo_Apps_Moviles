package com.example.sergio.breakfoodapp.restaurant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import com.example.sergio.breakfoodapp.ObjectSerializer;
import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

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
    private ImageView imgVistaPrevia;
    private ArrayList<Bitmap> imagenes;
    private ArrayList<String> stringAdapter;
    private ArrayAdapter<String> adapter;
    private static final int SELECT_FILE = 1;
    private Integer idrestaurant;
    private ArrayList<File> files;
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
                Bitmap bitmap = imagenes.get(position);
                imgVistaPrevia.setImageBitmap(bitmap);
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
            /*File file = files.get(i);
            String encodedBytes = null;
            try {
                encodedBytes = ObjectSerializer.serialize(file);
                nameValuePairs.add(new BasicNameValuePair("pictures", encodedBytes));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            try {
                Bitmap bitmap = imagenes.get(i);
                byte[] bytes = BitmapManager.bitmapToByteArray(bitmap);
                ArrayList<String> strings = BitmapManager.byteToStrings(bytes);
                String encodedBytes = ObjectSerializer.serialize(strings);
                nameValuePairs.add(new BasicNameValuePair("pictures", encodedBytes));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }

    //Creado por Bárbara Gutiérrez
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        Uri selectedImage;
        switch (requestCode)
        {
            case SELECT_FILE:
                if (resultCode == Activity.RESULT_OK)
                {
                    selectedImage = imageReturnedIntent.getData();
                    if(selectedImage != null)
                    {
                        //Obtiene el path de la imagen
                        String filePath = selectedImage.getPath();
                        //Obtiene la imagen en bitmap
                        File file = new File(filePath);
                        Bitmap bitmap = null;
                        InputStream imageStream = null;
                        try {
                            imageStream = getContentResolver().openInputStream(
                                    selectedImage);
                            bitmap = BitmapFactory.decodeStream(imageStream);
                            imgVistaPrevia.setImageBitmap(bitmap);
                            imagenes.add(bitmap); //Agrega la imagen al array de imagenes
                            files.add(file);
                            stringAdapter.add(file.getName()); //obtiene el nombre de la imagen y lo agrega
                            adapter.notifyDataSetChanged(); //actualiza el list view
                        }
                        catch (FileNotFoundException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }


}
