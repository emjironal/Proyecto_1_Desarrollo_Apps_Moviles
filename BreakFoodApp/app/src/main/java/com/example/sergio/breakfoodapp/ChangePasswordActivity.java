package com.example.sergio.breakfoodapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }




    public void changePassword(View view) {

        String password = ((EditText)findViewById(R.id.change_password_password_edittext)).getText().toString();
        String code =  ((EditText)findViewById(R.id.change_password_code_edittext)).getText().toString();

        if(!password.trim().equals("") && !code.trim().equals("")){
            String url = "https://appetyte.herokuapp.com/android/cambiarContrasena";
            List<NameValuePair> restaurantl = new ArrayList<>();
            restaurantl.add(new BasicNameValuePair("email", Controller.getInstance().getCorreo()));
            restaurantl.add(new BasicNameValuePair("password", password.trim()));
            restaurantl.add(new BasicNameValuePair("codigo", code.trim()));

            String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, restaurantl));

            JSONArray jsonArray = new JSONArray();
            try{
                //jsonArray = new JSONArray(result);
                JSONObject restaurant = new JSONObject(result);
                boolean valid = restaurant.getBoolean("result");
                if(valid){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"Codigo incorrecto",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Debe llenar los espacios",Toast.LENGTH_SHORT).show();
        }

    }
}
