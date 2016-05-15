package com.theb0ardside.up4stuff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void launchCreateEvent(View view) throws UnsupportedEncodingException {
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }

    public void launchViewEvents(View view) {
        Intent intent = new Intent(this, ViewEventsActivity.class);
        startActivity(intent);
    }
}
