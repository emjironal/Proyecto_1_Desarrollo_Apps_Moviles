package com.example.sergio.breakfoodapp;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.http.GestorGetRequest;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnEnter;
    private ImageButton btnFacebook;
    private TextView txtSignUp, txtRecuperar;

    //TODO: Agregar pantalla nuevo restaurante
    //TODO: Agregar LocatioPicker para esa pantalla
    //TODO: Agregar pantalla editar restaurante
    //TODO: Agregar Pantalla de imagenes
    //TODO: Agregar Pantalla de agregar imágenes


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        btnEnter = (Button)findViewById(R.id.btInicioSesion);        //apunta mi variable Button al botÃ³n del activity
        btnEnter.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                inicio();
            }
        });

        btnFacebook = (ImageButton) findViewById(R.id.btnInicioFacebook);        //apunta mi variable Button al botÃ³n del activity
        btnFacebook.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                inicioFacebook();
            }
        });



        txtSignUp=(TextView)findViewById(R.id.txtInicioRegistro);
        txtSignUp.setOnClickListener(signUpListener);


        txtRecuperar=(TextView)findViewById(R.id.txtInicioRecuperar);
        txtRecuperar.setOnClickListener(recuperar);

        String url = "http://192.168.0.10:8000/android/login";//cambiar a https://appetyte.herokuapp.com/
        List<NameValuePair> user = new ArrayList<>();
        user.add(new BasicNameValuePair("username", "mauri"));
        user.add(new BasicNameValuePair("password", "1234"));
        Log.e("Error", LectorHttpResponse.leer(GestorPostRequest.postData(url, user)));
        url = "http://192.168.0.10:8000/android/getRestaurantes";
        Log.e("Error", LectorHttpResponse.leer(GestorGetRequest.getData(url)));
    }


    public View.OnClickListener signUpListener = new View.OnClickListener() {


        public void onClick(View v) {
            // TODO Auto-generated method stub

            startActivity(new Intent(MainActivity.this,RegistroActivity.class));
        }
    };



    public View.OnClickListener recuperar = new View.OnClickListener() {


        public void onClick(View v) {
            // TODO Auto-generated method stub
            startActivity(new Intent(MainActivity.this,RecuperarActivity.class));
        }
    };



    public void inicio(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(MainActivity.this, BuscadorActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }


    public void inicioFacebook(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(MainActivity.this, FacebookReActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }
}

