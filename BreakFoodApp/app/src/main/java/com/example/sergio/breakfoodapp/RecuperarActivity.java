package com.example.sergio.breakfoodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.model.Restaurant;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecuperarActivity extends AppCompatActivity {
    private MixpanelAPI mixpanelAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();

        Button confirmButton = findViewById(R.id.retrieve_password_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText correoEditText = findViewById(R.id.retrieve_password_edittext);
                if (!correoEditText.getText().toString().trim().contains("@")){
                    Toast.makeText(getApplicationContext(),"Debe contener un correo válido",Toast.LENGTH_SHORT).show();
                }else{
                    //TODO: Hacer la petición de correo
                    String url = "https://appetyte.herokuapp.com/android/recuperarContrasena";
                    List<NameValuePair> restaurantl = new ArrayList<>();
                    restaurantl.add(new BasicNameValuePair("email", correoEditText.getText().toString().trim()));

                    String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, restaurantl));

                    JSONArray jsonArray = new JSONArray();
                    try{
                        //jsonArray = new JSONArray(result);
                        JSONObject restaurant = new JSONObject(result);
                        boolean valid = restaurant.getBoolean("result");
                        if(valid){
                            Controller.getInstance().setCorreo(correoEditText.getText().toString().trim());
                            Intent i = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Correo no válido",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}
