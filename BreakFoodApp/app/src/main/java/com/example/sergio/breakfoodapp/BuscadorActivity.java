package com.example.sergio.breakfoodapp;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class BuscadorActivity extends AbsRuntimePermission {

    private ImageButton btnEnter, btnSalir;
    private final int PERMISSIONS_REQUEST_MAP = 6546;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

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


    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if(requestCode == PERMISSIONS_REQUEST_MAP){
            //algo
        }
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
