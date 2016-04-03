package com.theb0ardside.up4stuff;

/**
 * Created by sideboard on 4/2/16.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Up4StuffClient {

    // Singleton

    private static final String BASE_URL = "http://68a087d1.ngrok.io/";

    private static Up4StuffClient mInstance = null;

    private AsyncHttpClient client;
    private PersistentCookieStore cookieStore;

    private Up4StuffClient() {
        client = new AsyncHttpClient();
    }

    private Context context;
    void init(Context context){
        cookieStore = new PersistentCookieStore(context.getApplicationContext());
        client.setCookieStore(cookieStore);
    }

    public static Up4StuffClient getInstance() {
        if (mInstance == null) {
            mInstance = new Up4StuffClient();
        }
        return mInstance;
    }

    // private static AsyncHttpClient client = new AsyncHttpClient();

//    public static void addCookieJar() {
//        client.setCookieStore(this);
//    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void printCookies() {
        System.out.println(cookieStore.getCookies());
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


}
