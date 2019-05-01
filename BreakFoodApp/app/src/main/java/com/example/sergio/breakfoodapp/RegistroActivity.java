package com.example.sergio.breakfoodapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    private Button btnEnter, btnRegistro;
    private EditText correo,contrasena,confirmacion;
    private MixpanelAPI mixpanelAPI;
    private CallbackManager callbackManager;
    LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);


        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();

        correo = (EditText)findViewById(R.id.etRegistroCorreo);
        contrasena = (EditText)findViewById(R.id.etRegistroContra);
        confirmacion = (EditText)findViewById(R.id.etRegistroConfi);


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
                aceptar();
            }
        });


        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) findViewById(R.id.login_button2);
        loginButton.setReadPermissions("email");
        // If using in a fragment
        //loginButton.setFragment();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainScreen();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.error_field_required, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), R.string.error_field_required, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void goMainScreen(){

        Intent intent = new Intent(this, BuscadorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }





    public void aceptar()
    {
        String url = "https://appetyte.herokuapp.com/android/insertarUsuario";

        String email = correo.getText().toString();
        String password = contrasena.getText().toString();
        String confirmPassword = confirmacion.getText().toString();

        if(confirmPassword.equals(password))
        {
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("username", email));


            HttpResponse response = GestorPostRequest.postData(url, nameValuePairs);
            String resultStr = LectorHttpResponse.leer(response);
            try
            {
                JSONObject jsonObject = new JSONObject(resultStr);
                boolean result = jsonObject.getBoolean("result");
                if(result)
                {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    //Crea el intent (nueva ventana)
                    Intent newScreen = new Intent(RegistroActivity.this, MainActivity.class);
                    //Inicia la nueva ventana
                    startActivity(newScreen);
                }
                else
                {
                    Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT).show();
                }
            }
            catch(JSONException e)
            {
                Log.e("Error", e.getMessage());
            }
        }
        else
        {
            Toast.makeText(this, "Contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
        }

    }


    public void Cancelar(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(RegistroActivity.this, MainActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }





}
