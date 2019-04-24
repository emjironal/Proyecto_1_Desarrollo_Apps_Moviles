package com.example.sergio.breakfoodapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    private Button btnEnter, btnRegistro;
    private EditText correo,contrasena,confirmacion;

   /* private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    LoginButton loginButton;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Profile profile=Profile.getCurrentProfile();
        Datos(profile);
    }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        /*callbackManager=CallbackManager.Factory.create();
        loginButton=(LoginButton)findViewById(R.id.login_button);*/

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
                Inicio();
            }
        });

       /* loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();

                Profile profile = Profile.getCurrentProfile();
                Datos(profile);
                accessTokenTracker=new AccessTokenTracker() {
                    @Override
                    protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {


                    }
                };
                profileTracker=new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                        Datos(currentProfile);
                    }
                };

                accessTokenTracker.startTracking();
                profileTracker.startTracking();

                loginButton.setReadPermissions("user_friends");
                loginButton.setReadPermissions("public_profile");



            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }


    private void Datos(Profile perfil){
        if(perfil != null){
            String nom=perfil.getName();

        }

*/
    }





    public void Inicio(){
        String miCorreo = null;
        String miContra = null;
        String miConfi = null;

        miCorreo = correo.getText().toString();
        miContra = contrasena.getText().toString();
        miConfi = confirmacion.getText().toString();

        if(miContra == miConfi) {

            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair();


            GestorPostRequest.postData("https://appetyte.herokuapp.com/android/login", lista);

            //Crea el intent (nueva ventana)
            Intent newScreen = new Intent(RegistroActivity.this, MainActivity.class);
            //Inicia la nueva ventana
            startActivity(newScreen);
        }

        else {

            //Crea el intent (nueva ventana)
            Intent newScreen = new Intent(RegistroActivity.this, MainActivity.class);
            //Inicia la nueva ventana
            startActivity(newScreen);

        }

    }


    public void Cancelar(){
        //Crea el intent (nueva ventana)
        Intent newScreen = new Intent(RegistroActivity.this, MainActivity.class);
        //Inicia la nueva ventana
        startActivity(newScreen);
    }





}
