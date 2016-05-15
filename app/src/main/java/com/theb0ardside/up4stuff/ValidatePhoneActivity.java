package com.theb0ardside.up4stuff;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ValidatePhoneActivity extends AppCompatActivity {

    public final static String PHONE = "com.theb0ardside.up4stuff.PHONE";
    private static final int REQUEST_SMS = 0;

    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 0;

    private TextView mPhoneyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_phone);

        mPhoneyTextView = (TextView) findViewById(R.id.phoneyRapper);
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            mPhoneyTextView.setText(telManager.getLine1Number());
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);

        }
        mPhoneyTextView.setText(telManager.getLine1Number());

        // TODO - back to MainActivity

    }

    public void sendMessageSignUp(View view) {
        Intent intent = new Intent(this, ValidatePhoneConfirmActivity.class);
        String phone = mPhoneyTextView.getText().toString();

        if ( !phone.isEmpty()) {
            try {
                postNumber(phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void postNumber(final String number) throws JSONException {
        RequestParams params = new RequestParams("phonenumber", number);
        Up4StuffClient.getInstance().post("user/create", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int stausCode, Header[] headers, JSONObject response) {
                System.out.println("JSONObject");
                // if the response was a JSONObject
                Intent intent = new Intent(getApplicationContext(), ValidatePhoneConfirmActivity.class);
                intent.putExtra(PHONE, number);
                startActivity(intent);
            }
        });
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_SMS);
    }

}
