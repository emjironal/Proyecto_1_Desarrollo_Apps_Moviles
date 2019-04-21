package com.example.sergio.breakfoodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectedRestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_restaurant);

        //TODO: obtener informacion del restaurante
        //TODO: Setear todos los datos (nombre, score, telefono, correo, web, horario, tipo de comida)

    }

    public void getAllCommments(View view) {
        //TODO: abre activity con todos los comentarios
    }
}
