package com.eduvanz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.FriendlyScoreUI.LaunchUI;
import com.eduvanz.MainApplication;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.google.gson.Gson;

import FriendlyScore.AppErrorForClient;
import FriendlyScore.Credentials;
import FriendlyScore.ErrorForUser;
import FriendlyScore.ReceivedTokenScore;
import FriendlyScore.ScoreCompleted;

/**
 * Created by projetctheena on 18/8/17.
 */

public class ClientActivity extends LaunchUI{

    /*
  Facebook
   */
    private static final int REQUEST_FACEBOOK_CODE = 1000;
    private CallbackManager fbCallbackManager;

    private final String SESSION_KEY_PREF = "SESSION_KEY_PREF";

    private  String SESSION_KEY_HEADER = "session-key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        checkGooglePlayServicesVersion();

        //Toolbar toolbar = new Toolbar(this);

        //setSupportActionBar(toolbar);

        Intent clientIntent = getIntent();
        boolean currentUser  = clientIntent.getBooleanExtra("currentUser", false);


        setUpFacebook();


        Log.d(MainApplication.TAG,"Session Key: "+getSessionKey());

        String appid = clientIntent.getStringExtra("app_id");
        Credentials credentials = null;
        String session_key = null;
        if(currentUser){


            if(clientIntent.hasExtra("friendlyScoreUser")){
                //Multiple Users per device, so client has to store User Object
                //That has session key in a sqlite database and pass it on.
                session_key = new Gson().fromJson(clientIntent.getStringExtra("friendlyScoreUser"),ReceivedTokenScore.class).session_key;

            }else{
                //One user per device. So Session Key for that user is unique
                session_key = getSessionKey();
            }


        }
        //Log.d(TAG,session_key);
        credentials = new Credentials(appid,session_key);

        credentials.getInstance();

    }


     /*
        Facebook Process

     */

    private void setUpFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());

        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
    }


    //The session is used by to connect your user to Friendly Score. So for a repeat user, the previous authentication
    //and scores are available.
    private String getSessionKey(){
        SharedPreferences sessionPreferences = this.getSharedPreferences(SESSION_KEY_PREF, Context.MODE_PRIVATE);

        String session_key = sessionPreferences.getString(SESSION_KEY_HEADER,null);

        Log.d(MainApplication.TAG, "Sesssion Key:"+session_key);
        return session_key;
    }



    @Override
    public void setClicked(int i, boolean b) {

    }

    @Override
    public void getCompletedScore(ScoreCompleted scoreCompleted) {

    }

    @Override
    public void handleReceivedTokenScore(ReceivedTokenScore receivedTokenScore) {

    }

    @Override
    public void appErrorMessage(AppErrorForClient appErrorForClient) {

    }

    @Override
    public void userErrorMessage(ErrorForUser errorForUser) {

    }

    @Override
    public boolean checkGooglePlayServicesVersion() {
        return false;
    }
}
