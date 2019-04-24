package com.example.sergio.breakfoodapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class FacebookReActivity extends AppCompatActivity {

    private MixpanelAPI mixpanelAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_re);

        mixpanelAPI = MixpanelAPI.getInstance(getApplicationContext(),getString(R.string.mixpanel_token));
        mixpanelAPI.track(this.getClass().getName());
        mixpanelAPI.flush();
    }
}
