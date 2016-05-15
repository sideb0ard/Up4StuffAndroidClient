package com.theb0ardside.up4stuff;

/**
 * Created by sideboard on 4/2/16.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Entity;
import android.content.Intent;
import android.webkit.CookieManager;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;


import java.net.CookiePolicy;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.entity.ContentType;


public class Up4StuffClient {

    // Singleton

    //private static final String BASE_URL = "http://cad1ebb7.ngrok.io/";

    private static Up4StuffClient mInstance = null;
    private PersistentCookieStore cookieStore = null;

    private AsyncHttpClient aclient;

    private Up4StuffClient() {

        aclient = new AsyncHttpClient();

    }

    public void initialize(Context context) {
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(context);
            aclient.setCookieStore(cookieStore);
        }
    }

    public static Up4StuffClient getInstance() {
        if (mInstance == null) {
            mInstance = new Up4StuffClient();
        }
        return mInstance;
    }


    public void showurl() {
        System.out.println(Constants.BASE_URL);
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        aclient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        aclient.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(Context ctxt, String url, HttpEntity data, String type, AsyncHttpResponseHandler responseHandler) {
        aclient.post(ctxt, getAbsoluteUrl(url), data, type, responseHandler );
    }

    public void printCookies() {
        System.out.println(cookieStore.getCookies());
    }

    public void delCookies() {
        cookieStore.clear();
    }

    public boolean haveSessionCookie() {

        // System.out.println("SESH COOKIE!" + cookieStore.getCookies());
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies.isEmpty()) {
            System.out.println("Nae COOKIES!\n\n");
            return false;
        } else {
            System.out.println("COOOOOOKIE MONSTER.\n");
            return true;
        }

    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return Constants.BASE_URL + relativeUrl;
    }


}
