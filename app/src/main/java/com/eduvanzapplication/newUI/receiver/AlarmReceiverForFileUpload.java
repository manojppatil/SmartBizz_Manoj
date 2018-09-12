package com.eduvanzapplication.newUI.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.services.MyServiceCallLog;
import com.eduvanzapplication.newUI.services.MyServiceReadSms;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

/**
 * Created by Vijay on 05-09-17.
 */

public class AlarmReceiverForFileUpload extends BroadcastReceiver {
  
    Context context;
    static String stringAllSmsContacts;
    public static String latestSmsDate = "";
    @Override
    public void onReceive(Context mcontext, Intent intent) {

        try {
            context = mcontext;

            Log.e(MainApplication.TAG, "Alarm received!: ");
            Log.e(TAG, "AlarmReceiverForFileUpload:" );
            /** getting data from shared preference **/
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String userMobileNo = sharedPreferences.getString("logged_id", "null");
            String userLoggedinID = sharedPreferences.getString("mobile_no", "null");

            Intent intentCallLog = new Intent(mcontext, MyServiceCallLog.class);
            mcontext.startService(intentCallLog);

            Intent intentReadSms = new Intent(mcontext, MyServiceReadSms.class);
            mcontext.startService(intentReadSms);
        } catch (Exception e) {
            e.printStackTrace();
        }


// created separate class
//        Intent intentCallStats = new Intent(mcontext, MyServiceCallStats.class);
//        mcontext.startService(intentCallStats);

// One Time
//        Intent intentContacts= new Intent(mcontext, MyServiceContacts.class);
//        mcontext.startService(intentContacts);

// created separate class
//        mcontext.startService(new Intent(context, LocationService.class));

    }


  
}


