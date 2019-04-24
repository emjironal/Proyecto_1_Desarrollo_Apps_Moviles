package com.example.sergio.breakfoodapp;

import android.content.Intent;
import android.database.ContentObservable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.http.GestorGetRequest;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.model.Restaurant;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnEnter;
    private TextView txtSignUp, txtRecuperar;
    private MixpanelAPI mixpanelAPI;
    private EditText correo,contrasena;

    //TODO: agregar TOKEN REAL
    public static final String MIXPANEL_TOKEN = "2e8bdc478fb999b0ffdfb0a8b06673ff";

    //TODO: Agregar pantalla nuevo restaurante
    //TODO: Agregar LocatioPicker para esa pantalla


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        correo = (EditText)findViewById(R.id.etCorreoInicio);
        contrasena = (EditText)findViewById(R.id.etContraInicio);


        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(), MIXPANEL_TOKEN);
        mixpanelAPI.track("Login Activity");
        mixpanelAPI.flush();

        btnEnter = (Button)findViewById(R.id.btInicioSesion);        //apunta mi variable Button al botÃ³n del activity
        btnEnter.setOnClickListener(new View.OnClickListener() {    //aquÃ­ le agrega el evento que "escucha" el click
            @Override
            public void onClick(View v) {
                inicio();
            }
        });



        txtSignUp=(TextView)findViewById(R.id.txtInicioRegistro);
        txtSignUp.setOnClickListener(signUpListener);


        txtRecuperar=(TextView)findViewById(R.id.txtInicioRecuperar);
        txtRecuperar.setOnClickListener(recuperar);
    }

    @Override
    protected void onDestroy() {
        mixpanelAPI.flush();
        super.onDestroy();
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



            startActivity(new Intent(MainActivity.this,ChangePasswordActivity.class));
        }
    };



    public void inicio(){


        String url = "https://appetyte.herokuapp.com/android/login";



        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("email", correo.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", contrasena.getText().toString()));


        HttpResponse response = GestorPostRequest.postData(url, nameValuePairs);
        String resultStr = LectorHttpResponse.leer(response);
        Log.i("resultado", resultStr);

        if(resultStr.equals("[]")){

            Toast.makeText(this, "El usuario no esta registrado", Toast.LENGTH_SHORT).show();
        }

        else {
            try {

                JSONArray jsonArray = new JSONArray(resultStr);
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                Controller.getInstance().setCorreo(jsonObject.getString("email"));
                Controller.getInstance().setUserID(jsonObject.getInt("iduser"));
                Controller.getInstance().setUsername(jsonObject.getString("username"));

                //Crea el intent (nueva ventana)
                Intent newScreen = new Intent(MainActivity.this, BuscadorActivity.class);
                //Inicia la nueva ventana
                startActivity(newScreen);

            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
            }
        }

        }


}



