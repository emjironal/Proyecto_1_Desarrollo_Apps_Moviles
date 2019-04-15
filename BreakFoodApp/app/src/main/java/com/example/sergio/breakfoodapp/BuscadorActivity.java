package com.example.sergio.breakfoodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class BuscadorActivity extends AppCompatActivity {

    private ImageButton btnEnter, btnSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);



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
