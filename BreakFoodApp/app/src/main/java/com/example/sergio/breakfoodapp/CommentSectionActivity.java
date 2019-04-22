package com.example.sergio.breakfoodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.adapters.CommentAdapter;
import com.example.sergio.breakfoodapp.http.GestorPostRequest;
import com.example.sergio.breakfoodapp.http.LectorHttpResponse;
import com.example.sergio.breakfoodapp.model.Comment;
import com.example.sergio.breakfoodapp.model.Restaurant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CommentSectionActivity extends AppCompatActivity {

    int idrestaurant;
    List<Comment> commentList;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_section);

        idrestaurant = getIntent().getIntExtra("idrestaurant",0);
        String name = getIntent().getStringExtra("restName");
        commentList = new ArrayList<>();

        ((TextView) findViewById(R.id.comment_section_rest_name)).setText(name);

        String url = "https://appetyte.herokuapp.com/android/getComentarios";
        List<NameValuePair> restaurantl = new ArrayList<>();
        restaurantl.add(new BasicNameValuePair("idrestaurant", ""+idrestaurant));

        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, restaurantl));

        JSONArray jsonArray = new JSONArray();
        Comment comment = new Comment();
        try{
            jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject commentObject = jsonArray.getJSONObject(i);
                comment = new Comment();
                comment.setContent(commentObject.getString("content"));
                comment.setOwner(commentObject.getString("username"));
                String date = commentObject.getString("datecreated");
                String time = commentObject.getString("timecreated");

                //TODO: formatear ambos fecha y tiempo

                commentList.add(comment);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        adapter = new CommentAdapter(commentList, this);

        RecyclerView recyclerView = findViewById(R.id.comment_section_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void submitComment(String content, String owner, String dateTime){
        //TODO: enviar comentario
        //TODO: setear nombre de usuario
    }
}
