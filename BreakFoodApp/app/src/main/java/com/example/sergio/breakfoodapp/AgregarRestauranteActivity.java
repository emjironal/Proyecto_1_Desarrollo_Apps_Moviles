package com.example.sergio.breakfoodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgregarRestauranteActivity extends AppCompatActivity {

    private MixpanelAPI mixpanelAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_restaurante);
        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(), getString(R.string.mixpanel_token));
        mixpanelAPI.track("AddResaurant Activity");
        mixpanelAPI.flush();
    }

    public void agregarRestaurante(View view) {
        //TODO: agregar restaurante....
        String nombre, precio, apertura, cierre;
        Double latitud, longitud;
        nombre = ((EditText) findViewById(R.id.etRestauranteNombre)).getText().toString();
        precio = ((EditText)findViewById(R.id.etRestaurantePrecio)).getText().toString();
        latitud = Double.parseDouble (((EditText) findViewById(R.id.etRestauranteLatitud)).getText().toString());
        longitud = Double.parseDouble (((EditText) findViewById(R.id.etRestauranteLonguitud)).getText().toString());
        apertura = ((EditText) findViewById(R.id.etRestauranteApertura)).getText().toString();
        cierre = ((EditText) findViewById(R.id.etRestauranteCierre)).getText().toString();
        String tipoComida = ((EditText) findViewById(R.id.etRestauranteDistrito)).getText().toString();
        if(!nombre.equals("") && !precio.equals("") && !apertura.equals("") && !apertura.equals("")){
            try{
                String url = "https://appetyte.herokuapp.com/android/insertarRestaurante";



                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("name", nombre));
                nameValuePairs.add(new BasicNameValuePair("latitudepos", latitud.toString()));
                nameValuePairs.add(new BasicNameValuePair("longitudepos", longitud.toString()));
                nameValuePairs.add(new BasicNameValuePair("open", apertura));
                nameValuePairs.add(new BasicNameValuePair("foodtype", tipoComida));
                nameValuePairs.add(new BasicNameValuePair("close", apertura));
                nameValuePairs.add(new BasicNameValuePair("price", precio));
                nameValuePairs.add(new BasicNameValuePair("codigodistrito", "11654"));
                HttpResponse response = GestorPostRequest.postData(url, nameValuePairs);
                String resultStr = LectorHttpResponse.leer(response);

                JSONObject jsonObject = new JSONObject(resultStr);

                boolean valid = jsonObject.getBoolean("result");
                if(valid){
                    Toast.makeText(getApplicationContext(),"Se agreg[o el restaurante",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(),"No se pudo insertar correctamete",Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e){

            }
        }else{
            Toast.makeText(getApplicationContext(), "Debe llenar todos los espacios",Toast.LENGTH_SHORT).show();
        }
    }
}
