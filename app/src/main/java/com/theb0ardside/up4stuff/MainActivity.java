package com.theb0ardside.up4stuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Up4StuffClient.getInstance();
        Up4StuffClient.getInstance().init(this);
        //Up4StuffClient.getInstance().delCookies();

        if (logged_in_check()) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        }

    }

    private boolean logged_in_check() {
        //Up4StuffClient.getInstance().printCookies();
        return Up4StuffClient.getInstance().getSessionCookie();

    }

}
