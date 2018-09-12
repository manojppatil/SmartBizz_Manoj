package com.eduvanzapplication.newUI.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.services.MyServiceAppStats;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

/**
 * Created by Vijay on 05-09-17.
 */

public class AppStatsForFileUpload extends BroadcastReceiver {
  
    Context context;
    static String stringAllSmsContacts;
    public static String latestSmsDate = "";
    @Override
    public void onReceive(Context mcontext, Intent intent) {
        try {
            context = mcontext;

            Log.e(MainApplication.TAG, "Alarm received!: ");
            Log.e(TAG, "AppStatsForFileUpload : APP STATS BROADCAST " );
            /** getting data from shared preference **/
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String userMobileNo = sharedPreferences.getString("logged_id", "null");
            String userLoggedinID = sharedPreferences.getString("mobile_no", "null");

            Intent intentCallStats = new Intent(mcontext, MyServiceAppStats.class);
            mcontext.startService(intentCallStats);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

  
}


