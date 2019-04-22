package com.example.sergio.breakfoodapp.restaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sergio.breakfoodapp.Controller;
import com.example.sergio.breakfoodapp.R;
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


        final View commentLayout = findViewById(R.id.restaurant_profile_comment_layout);
        commentLayout.findViewById(R.id.comment_submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment(((EditText)commentLayout.findViewById(R.id.comment_content_edit_text)).getText().toString(), Controller.getInstance().getUserID());
            }
        });



    }

    private void submitComment(String content, int ownerID){
        String url = "https://appetyte.herokuapp.com/android/comentar";
        List<NameValuePair> comment = new ArrayList<>();
        comment.add(new BasicNameValuePair("idrestaurant", ""+idrestaurant));
        comment.add(new BasicNameValuePair("content",content));
        comment.add(new BasicNameValuePair("iduser", "" + ownerID));
        String result = LectorHttpResponse.leer(GestorPostRequest.postData(url, comment));

    }
}
