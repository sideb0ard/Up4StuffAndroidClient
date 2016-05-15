package com.theb0ardside.up4stuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CreateEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    public void createEvent(View button) {
        final EditText titleField = (EditText) findViewById(R.id.EditEventTitle);
        String title = titleField.getText().toString();

        final EditText whereField = (EditText) findViewById(R.id.EditEventWhere);
        String where = whereField.getText().toString();

        final EditText descriptionField = (EditText) findViewById(R.id.EditEventDescription);
        String description = descriptionField.getText().toString();

        JSONObject event = new JSONObject();
        try {
            event.put("title", title);
            event.put("where", where);
            event.put("description", description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("CREATE", "json: " + event.toString());
        StringEntity stringevent = null;
        try {
            stringevent = new StringEntity(event.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Up4StuffClient.getInstance().post(this, "/event/create", stringevent, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println(response);

                // if the response was a JSONObject
            }
        });

        Intent intent =  new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}
