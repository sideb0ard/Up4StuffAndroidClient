package com.theb0ardside.up4stuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler db = new DBHandler(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Up4StuffClient.getInstance().initialize(this);

        // Up4StuffClient.getInstance().delCookies();
        //clear_prefz();

        check_credz();
        //logged_in_check();

    }

    private boolean logged_in_check() {
        Up4StuffClient.getInstance().printCookies();
        // TODO: check for GCM token
        return Up4StuffClient.getInstance().haveSessionCookie();

    }

    private void check_credz() {

        Map<String, ?> keys = sharedPreferences.getAll();
        Log.d("Keys Empty?", String.valueOf(keys.isEmpty()));
        Log.d("KEYS SIZE:", String.valueOf(keys.size()));
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("Prefz vals", entry.getKey() + ": " +
                    entry.getValue().toString());
        }

        TextView textView = (TextView) findViewById(R.id.mainStartUpTextView);


        if (!sharedPreferences.getBoolean(Preferences.PHONE_VALIDATED, false)) {
            Log.d("PHONEY", "not registered");
            register_phone();
        } else {
            Log.d("PHONEY", "RRRRRegistered");
        }

        if (!sharedPreferences.getBoolean(Preferences.GCM_REGISTERED, false)) {
            Log.d("GCM", "not registered");
            register_gcm();
        } else {
            Log.d("GCM", "Rrrregistered");

            //register_gcm();
        }


        Log.d("REGISTER", "yarly");
        textView.setText("WOOHOO");
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);

//        boolean sentToken = sharedPreferences
//                .getBoolean(Preferences.SENT_TOKEN_TO_SERVER, false);


    }

    private void clear_prefz() {
        sharedPreferences.edit().clear().apply();

    }

    private void register_phone() {
        Log.d("MAIN", "Register_phone called.");
        Intent intent = new Intent(getApplicationContext(), ValidatePhoneActivity.class);
        startActivity(intent);
        // sharedPreferences.edit().putBoolean(Preferences.PHONE_VALIDATED, true).apply();
    }

    private void register_gcm() {
        Log.d("MAIN", "Register_GCM called.");
        Intent intent = new Intent(getApplicationContext(), RegisterForGoogleCloudMessaging.class);
        startActivity(intent);
    }

}
