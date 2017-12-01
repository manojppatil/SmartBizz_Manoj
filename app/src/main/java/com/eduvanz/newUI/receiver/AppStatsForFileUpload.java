package com.eduvanz.newUI.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.newUI.services.MyServiceCallStats;

import static com.eduvanz.newUI.MainApplication.TAG;

/**
 * Created by Darsh on 05-09-17.
 */

public class AppStatsForFileUpload extends BroadcastReceiver {
  
    Context context;
    static String stringAllSmsContacts;
    public static String latestSmsDate = "";
    @Override
    public void onReceive(Context mcontext, Intent intent) {
        context = mcontext;

        Log.e(MainApplication.TAG, "Alarm received!: ");
        Log.e(TAG, "AppStatsForFileUpload : APP STATS BROADCAST " );
        /** getting data from shared preference **/
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userMobileNo = sharedPreferences.getString("logged_id", "null");
        String userLoggedinID = sharedPreferences.getString("mobile_no", "null");

        Intent intentCallStats = new Intent(mcontext, MyServiceCallStats.class);
        mcontext.startService(intentCallStats);

    }


  
}


