package com.example.sergio.breakfoodapp;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.http.GestorGetRequest;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuscadorActivity extends AbsRuntimePermission {

    private ImageButton btnEnter, btnSalir;
    private final int PERMISSIONS_REQUEST_MAP = 6546;
    private Spinner opciones ;
    private MixpanelAPI mixpanelAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();

        requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE},
                R.string.msg,
                PERMISSIONS_REQUEST_MAP);

        btnEnter = (ImageButton) findViewById(R.id.btBuscador);        //apunta mi variable Button al botÃ³n del activity
        btnEnter.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                inicio();
            }
        });


        btnEnter = (ImageButton) findViewById(R.id.btBuscardorSalir);        //apunta mi variable Button al botÃ³n del activity
        btnEnter.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                salir();
            }
        });


       opciones = (Spinner)findViewById(R.id.spnBuscarComida);

       //TODO: Llenar array con datos reales

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getFoodTypeData());
        opciones.setAdapter(adapter);


    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if(requestCode == PERMISSIONS_REQUEST_MAP){
            //algo
        }
    }


    private ArrayList<String> getFoodTypeData()
    {
        String url = "https://appetyte.herokuapp.com/android/getAllFoodtypes";
        String result = LectorHttpResponse.leer(GestorGetRequest.getData(url));
        ArrayList<String> foodtypes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject respuesta = jsonArray.getJSONObject(i);
                String foodtype = respuesta.getString("foodtype");
                foodtypes.add(foodtype);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return foodtypes;
    }


    public void inicio(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(BuscadorActivity.this, ResultadoActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }


    public void salir(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(BuscadorActivity.this, MainActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }


}
