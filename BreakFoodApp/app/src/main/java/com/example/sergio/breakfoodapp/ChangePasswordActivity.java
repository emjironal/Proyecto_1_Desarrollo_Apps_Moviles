package com.example.sergio.breakfoodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }




    public void changePasswors(View view) {

        String password = ((EditText)findViewById(R.id.change_password_password_edittext)).getText().toString();
        String code =  ((EditText)findViewById(R.id.change_password_code_edittext)).getText().toString();



    }
}
