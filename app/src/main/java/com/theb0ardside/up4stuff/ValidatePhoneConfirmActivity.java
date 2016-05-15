package com.theb0ardside.up4stuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ValidatePhoneConfirmActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_confirmation);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void sendMessageConfirmation(View view) {
        Intent prevIntent = getIntent();
        String phone = prevIntent.getStringExtra(ValidatePhoneActivity.PHONE);
        // Intent intent = new Intent(this, ValidatePhoneConfirmActivity.class);
        EditText editText = (EditText) findViewById(R.id.confirmation);
        String code = editText.getText().toString();

        if ( !code.isEmpty()) {
            try {
                RequestParams params = new RequestParams("token", code);
                params.put("phonenumber", phone);
                Up4StuffClient.getInstance().post("user/validate", params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int stausCode, Header[] headers, JSONObject response) {
                        System.out.println("SUCCESS!");
                        // if the response was a JSONObject
                        // Up4StuffClient.getInstance().printCookies();
                        sharedPreferences.edit().putBoolean(Preferences.PHONE_VALIDATED, true).apply();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
