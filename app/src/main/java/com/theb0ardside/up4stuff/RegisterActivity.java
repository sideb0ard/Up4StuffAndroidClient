package com.theb0ardside.up4stuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RegisterActivity extends AppCompatActivity {

    public final static String PHONE = "com.theb0ardside.up4stuff.PHONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // TODO : find cleaner method for adding application context to client cookie store
        Up4StuffClient.getInstance();
        Up4StuffClient.getInstance().init(this);
    }

    public void sendMessageSignUp(View view) {
        Intent intent = new Intent(this, DisplayConfirmationActivity.class);
        EditText editText = (EditText) findViewById(R.id.phone);
        String phone = editText.getText().toString();

        if ( !phone.isEmpty()) {
            try {
                new CallAPI().postNumber(phone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public class CallAPI {
        public void getHello() throws JSONException {

            Up4StuffClient.getInstance().get("/", null, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int stausCode, Header[] headers, JSONObject response) {
                    // if the response was a JSONObject
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray replies) {
                    // if it was JSONArray
                }
            });
        }
        private void postNumber(final String number) throws JSONException {
            RequestParams params = new RequestParams("phonenumber", number);
            Up4StuffClient.getInstance().post("user/create", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int stausCode, Header[] headers, JSONObject response) {
                    System.out.println("JSONObject");
                    // if the response was a JSONObject
                    Intent intent = new Intent(getApplicationContext(), DisplayConfirmationActivity.class);
                    intent.putExtra(PHONE, number);
                    startActivity(intent);
                }
            });
        }
    }
}
