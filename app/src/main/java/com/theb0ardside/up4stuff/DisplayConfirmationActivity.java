package com.theb0ardside.up4stuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DisplayConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_confirmation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void sendMessageConfirmation(View view) {
        Intent prevIntent = getIntent();
        String phone = prevIntent.getStringExtra(RegisterActivity.PHONE);
        // Intent intent = new Intent(this, DisplayConfirmationActivity.class);
        EditText editText = (EditText) findViewById(R.id.confirmation);
        String code = editText.getText().toString();

        if ( !code.isEmpty()) {
            try {
                new CallAPI().postConfirmation(code, phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class CallAPI {
        public void postConfirmation(final String code, final String phone) throws JSONException {
            RequestParams params = new RequestParams("token", code);
            params.put("phonenumber", phone);
            Up4StuffClient.post("user/validate", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int stausCode, Header[] headers, JSONObject response) {
                    System.out.println("SUCCESS!");
                    // if the response was a JSONObject
//                    Intent intent = new Intent(getApplicationContext(), DisplayConfirmationActivity.class);
//                    intent.putExtra(EXTRA_MESSAGE, "yar (jobject) - type code here");
//                    intent.putExtra(PHONE, number);
//                    startActivity(intent);
                }
            });
        }
    }

}