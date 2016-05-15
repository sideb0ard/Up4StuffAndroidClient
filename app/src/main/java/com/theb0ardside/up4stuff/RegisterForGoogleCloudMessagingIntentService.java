package com.theb0ardside.up4stuff;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sideboard on 4/12/16.
 */

public class RegisterForGoogleCloudMessagingIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    private SharedPreferences sharedPreferences;

    public RegisterForGoogleCloudMessagingIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // [START register_for_gcm]
            // Initially this call goes out to the network to retrieve the token, subsequent calls
            // are local.
            // R.string.gcm_defaultSenderId (the Sender ID) is typically derived from google-services.json.
            // See https://developers.google.com/cloud-messaging/android/start for details on this file.
            // [START get_token]
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);


            sendRegistrationToServer(token);

            // Subscribe to topic channels
            subscribeTopics(token);

            sharedPreferences.edit().putBoolean(Preferences.GCM_REGISTERED, true).apply();

        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(Preferences.GCM_REGISTERED, false).apply();
        }

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Preferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }


    private void sendRegistrationToServer(String token) throws JSONException {

        System.out.println("YARLY BOB - " + token);

        RequestParams params = new RequestParams("gcm_token", token);

        SyncHttpClient sclient = new SyncHttpClient();
        PersistentCookieStore cookieStore = new PersistentCookieStore(this);
        sclient.setCookieStore(cookieStore);
        sclient.post(Constants.BASE_URL+"user/gcm", params, new JsonHttpResponseHandler () {
            @Override
            public void onSuccess(int stausCode, Header[] headers, JSONObject response) {
                System.out.println("YA WEE DANCER!");
                Log.d("GCMSTUFF", response.toString());
                sharedPreferences.edit().putBoolean(Preferences.GCM_REGISTERED, true).apply();
            }

            public void onFailure(int stausCode, Header[] headers, JSONObject response) {
                System.out.println("YA DIRTY PRANCER!");
                Log.d("GCMSTUFF", response.toString());
                sharedPreferences.edit().putBoolean(Preferences.GCM_REGISTERED, false).apply();
            }
        });


    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
    // [END subscribe_topics]



}

