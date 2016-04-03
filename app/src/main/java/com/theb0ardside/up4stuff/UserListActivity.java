package com.theb0ardside.up4stuff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

    }

    public void printUserList(View view) {

        try {
            new CallAPI().getUserList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class CallAPI {
        public void getUserList() throws JSONException {

            Up4StuffClient.getInstance().get("/user/list", null, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    System.out.println(response);
                    // if the response was a JSONObject
                }
            });
        }
    }
}
