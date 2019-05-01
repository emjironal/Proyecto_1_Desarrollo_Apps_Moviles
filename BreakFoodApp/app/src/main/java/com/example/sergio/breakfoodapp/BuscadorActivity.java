package com.example.sergio.breakfoodapp;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.http.GestorGetRequest;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.location.SingleShotLocationProvider;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscadorActivity extends AbsRuntimePermission {

    private ImageButton btnEnter, btnSalir;
    private final int PERMISSIONS_REQUEST_MAP = 6546;
    private Spinner spinnerTipoComida;
    private Spinner spinnerDistancia;
    private Spinner spinnerPrecio;
    private Spinner spinnerCalificacion;
    private MixpanelAPI mixpanelAPI;
    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();

        requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                R.string.msg,
                PERMISSIONS_REQUEST_MAP);

        btnEnter = (ImageButton) findViewById(R.id.btBuscador);        //apunta mi variable Button al botÃ³n del activity
        btnEnter.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                String nombreRest = ((EditText) findViewById(R.id.editTextRestauranteNombre)).getText().toString();
                String precio = spinnerPrecio.getSelectedItem().toString();
                String distancia = spinnerDistancia.getSelectedItem().toString();
                String calificacion = spinnerCalificacion.getSelectedItem().toString();
                String tipoComida = spinnerTipoComida.getSelectedItem().toString();
                buscar(precio,distancia,nombreRest,calificacion,tipoComida);
            }
        });


        btnEnter = (ImageButton) findViewById(R.id.btBuscardorSalir);        //apunta mi variable Button al botÃ³n del activity
        btnEnter.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                salir();
            }
        });

        //Asigna spinners
        spinnerTipoComida = (Spinner)findViewById(R.id.spnTipoComida);
        spinnerPrecio = findViewById(R.id.spnPrecioComida);
        spinnerCalificacion = findViewById(R.id.spnCalificacionComida);
        spinnerDistancia = findViewById(R.id.spnDistanciaRestaurante);

        //ArrayList de strings
        String[] precios = {"Cualquiera","Barato","Medio","Caro"};
        String[] calificaciones = {"Cualquiera","1","2","3","4","5"};
        String[] distancias = {"Cualquiera","5","10","50","100","200"};


       //TODO: Llenar array con datos reales

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getFoodTypeData());
        spinnerTipoComida.setAdapter(adapterTipo);

        ArrayAdapter<String> adapterPrecio = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, precios);
        spinnerPrecio.setAdapter(adapterPrecio);

        ArrayAdapter<String> adapterCalificacion = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, calificaciones);
        spinnerCalificacion.setAdapter(adapterCalificacion);


        ArrayAdapter<String> adapterDistancia = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, distancias);
        spinnerDistancia.setAdapter(adapterDistancia);

    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if(requestCode == PERMISSIONS_REQUEST_MAP){
            //algo
        }
    }

    private void getCurrentGPS(){
        SingleShotLocationProvider.requestSingleUpdate(this,new SingleShotLocationProvider.LocationCallback() {
            @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                longitude = location.longitude;
                latitude = location.longitude;
                Log.i("LocationGPSCallback",String.format("latutude: %s, longitude: %s",latitude, longitude));
            }
        });
    }



    private void buscar(String precio, String distancia, String nombreRestaurante, String calificacion, String tipoComida){
        getCurrentGPS();
        Intent newScreen = new Intent(BuscadorActivity.this, ResultadoActivity.class);
        newScreen.putExtra("precio",precio);
        newScreen.putExtra("nombreRestaurante",nombreRestaurante);
        newScreen.putExtra("calificacion",calificacion);
        newScreen.putExtra("tipo",tipoComida);
        newScreen.putExtra("latitude",latitude);
        newScreen.putExtra("longitude",longitude);
        newScreen.putExtra("distancia",distancia);
        startActivity(newScreen);
    }


    private ArrayList<String> getFoodTypeData()
    {
        String url = "https://appetyte.herokuapp.com/android/getAllFoodtypes";
        String result = LectorHttpResponse.leer(GestorGetRequest.getData(url));
        ArrayList<String> foodtypes = new ArrayList<>();
        foodtypes.add("Cualquiera");
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

        LoginManager.getInstance().logOut();
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(BuscadorActivity.this, MainActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }


}
