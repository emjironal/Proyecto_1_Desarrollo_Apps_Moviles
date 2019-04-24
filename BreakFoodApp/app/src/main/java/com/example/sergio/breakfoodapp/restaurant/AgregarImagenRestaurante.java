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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_imagen_restaurante);
        idrestaurant = getIntent().getIntExtra("idrestaurant",0);

        imgVistaPrevia = findViewById(R.id.imgVistaPrevia);
        imagenes = new ArrayList<>();
        stringAdapter = new ArrayList<>();

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
        String pictures = "[";
        for(int i = 0; i < imagenes.size(); i++)
        {
            Bitmap bitmap = imagenes.get(i);
            byte[] bytes = BitmapManager.bitmapToByteArray(bitmap);
            String encodedBytes = ObjectSerializer.encodeBytes(bytes);
            pictures += encodedBytes;
            if(i < imagenes.size() - 1)
            {
                pictures += ",";
            }
        }
        pictures += "]";
        nameValuePairs.add(new BasicNameValuePair("pictures", pictures));
        HttpResponse response = GestorPostRequest.postData(url, nameValuePairs);
        String resultStr = LectorHttpResponse.leer(response);
        try
        {
            JSONObject jsonObject = new JSONObject(resultStr);
            boolean result = jsonObject.getBoolean("result");
            if(result)
            {
                Toast.makeText(this, "Las imágenes se agregaron", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Las imágenes no se agregaron", Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e)
        {
            Log.e("Error", e.getMessage());
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
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        imgVistaPrevia.setImageBitmap(bitmap);
                        imagenes.add(bitmap); //Agrega la imagen al array de imagenes
                        File file = new File(filePath);
                        stringAdapter.add(file.getName()); //obtiene el nombre de la imagen y lo agrega
                        adapter.notifyDataSetChanged(); //actualiza el list view
                    }
                }
                break;
        }
    }


}
