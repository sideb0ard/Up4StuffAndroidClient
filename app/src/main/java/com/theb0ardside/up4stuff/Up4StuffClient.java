package com.theb0ardside.up4stuff;

/**
 * Created by sideboard on 4/2/16.
 */

import android.app.Activity;
import android.content.Intent;

import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Up4StuffClient {

    private static final String BASE_URL = "http://68a087d1.ngrok.io/";

    private static AsyncHttpClient client = new AsyncHttpClient();

//    public static void addCookieJar() {
//        client.setCookieStore(this);
//    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


}
