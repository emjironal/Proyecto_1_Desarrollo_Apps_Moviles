package com.example.sergio.breakfoodapp;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.facebook.*;

public class RegistroActivity extends AppCompatActivity {

    private Button btnEnter, btnRegistro;
    private ImageButton btnFacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        btnEnter = (Button)findViewById(R.id.btRegistroCancelar);        //apunta mi variable Button al botÃ³n del activity
        btnEnter.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                Cancelar();
            }
        });

        btnRegistro = (Button)findViewById(R.id.btRegistroAceptar);        //apunta mi variable Button al botÃ³n del activity
        btnRegistro.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                Inicio();
            }
        });


        btnFacebook = (ImageButton)findViewById(R.id.btRegistroFacebook);        //apunta mi variable Button al botÃ³n del activity
        btnFacebook.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                irFacebook();
            }
        });
    }


    public void Inicio(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(RegistroActivity.this, MainActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }


    public void Cancelar(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(RegistroActivity.this, MainActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }



    public void irFacebook(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(RegistroActivity.this, FacebookReActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }
}
