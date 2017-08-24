package com.eduvanz.friendlyscore;

import android.accounts.Account;
import android.app.Activity;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import FriendlyScore.Constants;
import FriendlyScore.SendToFriendlyScore;

/**
 * Created by Deepak Bhatia on 03/04/2017.
 *
 *
 * Copyright Friendly Score
 */

public class GoogleServerAuthCodeTask extends AsyncTask<String, Void, JSONObject> {


    String TAG = "GoogleServerAuthCodeTask";

    Activity callAct;
    public GoogleServerAuthCodeTask(Activity act){

        callAct = act;
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        HttpURLConnection urlConnection = null;

        JSONObject googleToken = new JSONObject();

        String mEmail = params[0];
        String mType = params[1];
        String idToken = params[2];
        try {
            URL url = new URL("https://www.googleapis.com/plus/v1/people/me");

            Account account = new Account(mEmail, mType);

            String scope = Constants.GOOGLE_SERVER_CODE_SCOPE;
            String token =  GoogleAuthUtil.getTokenWithNotification(callAct, account, scope,null);



            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);

            BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }



            Calendar cal = Calendar.getInstance();

            long currentTime = cal.getTimeInMillis();
            cal.setTimeInMillis(currentTime);

            cal.add(Calendar.HOUR, 1);

            googleToken.put("id_token",idToken);
            googleToken.put("access_token",token);
            /*googleToken.put("created", currentTime);
            googleToken.put("token_type","bearer");

            googleToken.put("expires_in",cal.getTimeInMillis());
         */

        } catch (UserRecoverableAuthException userAuthEx) {
            // Start the user recoverable action using the intent returned by
            // getIntent()
            //startActivityForResult(userAuthEx.getIntent(), RC_SIGN_IN);

            return null;
        } catch (Exception e) {
            // Handle error
            // e.printStackTrace(); // Uncomment if needed during debugging.
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return googleToken;
    }

    @Override
    protected void onPostExecute(JSONObject info) {
        // Store or use the user's email address
        if(info!=null)
            new SendToFriendlyScore(callAct).execute(info.toString(), ""+ Constants.GOOGLE_ID);
        else{
            //TODO(Inform User to Add Appropriate Message For User)
        }
    }

}
