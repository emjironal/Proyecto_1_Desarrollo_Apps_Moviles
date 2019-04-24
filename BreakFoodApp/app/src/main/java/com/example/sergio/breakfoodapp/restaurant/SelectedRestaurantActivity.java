package com.example.sergio.breakfoodapp.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergio.breakfoodapp.Controller;
import com.example.sergio.breakfoodapp.R;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.model.Restaurant;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectedRestaurantActivity extends AppCompatActivity {


    int idrestaurant;
    String restName;
    private MixpanelAPI mixpanelAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_restaurant);

        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();

        idrestaurant = getIntent().getIntExtra("idrestaurant",1);

        String url = "https://appetyte.herokuapp.com/android/getRestaurante";
        List<NameValuePair> restaurantl = new ArrayList<>();
        restaurantl.add(new BasicNameValuePair("idrestaurant", ""+idrestaurant));

        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, restaurantl));

        JSONArray jsonArray = new JSONArray();
        Restaurant r1 = new Restaurant();
        try{
            jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                r1 = new Restaurant();
                JSONObject restaurant = jsonArray.getJSONObject(i);
                r1.setName(restaurant.getString("name"));
                r1.setLat(restaurant.getDouble("latitudepos"));
                r1.setLongitude(restaurant.getDouble("longitudepos"));
                r1.setPrice(restaurant.getString("price"));
                r1.setOpen(restaurant.getString("open"));
                r1.setClose(restaurant.getString("close"));
                r1.setFoodType(restaurant.getString("foodtype"));
                r1.setScore(restaurant.getDouble("realscore"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        restName = r1.getName();


        TextView name = findViewById(R.id.restaurant_profile_name);
        TextView schedule = findViewById(R.id.restaurant_profile_schedule);
        TextView foodType = findViewById(R.id.restaurant_profile_foodType);
        TextView priceRange = findViewById(R.id.restaurant_profile_price_point);
        TextView score = findViewById(R.id.restaurant_profile_scorenumber);

        name.setText(r1.getName());
        schedule.setText(String.format("%s - %s",r1.getOpen(), r1.getClose()));
        foodType.setText(r1.getFoodType());
        priceRange.setText(r1.getPrice());
        score.setText(String.format("%.2f", r1.getScore()));


        //TODO: importar una imagen
        //TODO: obtener puntuación dada a este restaurante


        final View commentLayout = findViewById(R.id.restaurant_profile_comment_layout);
        commentLayout.findViewById(R.id.comment_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((EditText)commentLayout.findViewById(R.id.comment_content_edit_text)).getText().toString().equals("")){
                    submitComment(((EditText)commentLayout.findViewById(R.id.comment_content_edit_text)).getText().toString(), Controller.getInstance().getUserID());
                }else{
                    Toast.makeText(getApplicationContext(),"Debe escribir algo",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void getAllCommments(View view) {
        Intent i = new Intent(getApplicationContext(), CommentSectionActivity.class);
        i.putExtra("idrestaurant", idrestaurant);
        i.putExtra("restName",restName);
        startActivity(i);
    }


    public void submitScore(View view) {

        String n =  (String) view.getTag();
        int number = Integer.parseInt(n);
        switch (number){
            case 1:{
                ((ImageView) findViewById(R.id.restaurant_profile_star1)).setImageResource(R.drawable.ic_star_yellow_24dp);
            }
            break;
            case 2:{
                ((ImageView) findViewById(R.id.restaurant_profile_star1)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star2)).setImageResource(R.drawable.ic_star_yellow_24dp);
            }
            break;
            case 3:{
                ((ImageView) findViewById(R.id.restaurant_profile_star1)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star2)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star3)).setImageResource(R.drawable.ic_star_yellow_24dp);
            }
            break;
            case 4:{
                ((ImageView) findViewById(R.id.restaurant_profile_star1)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star2)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star3)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star4)).setImageResource(R.drawable.ic_star_yellow_24dp);
            }
            break;
            default:
                ((ImageView) findViewById(R.id.restaurant_profile_star1)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star2)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star3)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star4)).setImageResource(R.drawable.ic_star_yellow_24dp);
                ((ImageView) findViewById(R.id.restaurant_profile_star5)).setImageResource(R.drawable.ic_star_yellow_24dp);

        }

        findViewById(R.id.restaurant_profile_star1).setClickable(false);
        findViewById(R.id.restaurant_profile_star2).setClickable(false);
        findViewById(R.id.restaurant_profile_star3).setClickable(false);
        findViewById(R.id.restaurant_profile_star4).setClickable(false);
        findViewById(R.id.restaurant_profile_star5).setClickable(false);
        //TODO: enviar score
        submitScore(number);

    }

    private void submitScore(int n){
        //TODO: enviar score, iduser, idrestaurant
        String url = "https://appetyte.herokuapp.com/android/calificarResturante";
        List<NameValuePair> comment = new ArrayList<>();
        comment.add(new BasicNameValuePair("idrestaurant", Integer.toString(idrestaurant)));
        comment.add(new BasicNameValuePair("score",Integer.toString(n)));
        comment.add(new BasicNameValuePair("iduser", "" + Controller.getInstance().getUserID()));
        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, comment));
        try {
            JSONObject respuesta = new JSONObject(result);
            boolean valid = respuesta.getBoolean("result");
            if (valid){
                Toast.makeText(getApplicationContext(),"Calificación subida correctamente",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Error al subir calificación",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void submitComment(String content, int ownerID){
        String url = "https://appetyte.herokuapp.com/android/comentar";
        List<NameValuePair> comment = new ArrayList<>();
        comment.add(new BasicNameValuePair("idrestaurant", Integer.toString(idrestaurant)));
        comment.add(new BasicNameValuePair("content",content));
        comment.add(new BasicNameValuePair("iduser", Integer.toString(ownerID)));
        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, comment));

        try {
            JSONObject respuesta = new JSONObject(result);
            boolean valid = respuesta.getBoolean("result");
            if (valid){
                Toast.makeText(getApplicationContext(),"Comentario subido correctamente",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"Error al subir comentario",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void printMessage(String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }


    public void openGallery(View view) {

        Intent i = new Intent(getApplicationContext(), RestaurantGalleryActivity.class);
        i.putExtra("idrestaurant", idrestaurant);
        i.putExtra("restName",restName);
        startActivity(i);

    }
}
