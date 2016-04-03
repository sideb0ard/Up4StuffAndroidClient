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

import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;

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


    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void printCookies() {
        System.out.println(cookieStore.getCookies());
    }

    public void delCookies() {
        cookieStore.clear();
    }

    public boolean getSessionCookie() {

        // System.out.println("SESH COOKIE!" + cookieStore.getCookies());
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies.isEmpty()) {
            System.out.println("Nae COOKIES!\n\n");
            return false;
        } else {
            return true;
        }

    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


}
