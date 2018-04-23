package com.eduvanz.newUI.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.newUI.services.LocationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.eduvanz.newUI.MainApplication.TAG;

/**
 * Created by Darsh on 05-09-17.
 */

public class LocationUploadForFileUpload extends BroadcastReceiver {
  
    Context context;
    static String stringAllSmsContacts,appInstallationTimeStamp;
    public static String latestSmsDate = "";
    @Override
    public void onReceive(Context mcontext, Intent intent) {
        context = mcontext;

        Log.e(MainApplication.TAG, "Alarm received!: ");
        Log.e(TAG, "LocationUploadForFileUpload : LOCATION " );
        /** getting data from shared preference **/
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userMobileNo = sharedPreferences.getString("logged_id", "null");
        String userLoggedinID = sharedPreferences.getString("mobile_no", "null");
        appInstallationTimeStamp = sharedPreferences.getString("appInstallationTimeStamp", "");
        try {
            Log.e(MainApplication.TAG, "Alarm received!: ");
            /** getting data from shared preference **/

            Log.e(TAG, "onCreate: "+appInstallationTimeStamp );
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date lLocationDate= simpleDateFormat.parse(appInstallationTimeStamp);
                if(lLocationDate != null) {
                    long installationTime = lLocationDate.getTime() + (5 * 12 * 60 * 60 * 1000);
                    Log.e(TAG, "onCreate:installationTime+5 days " + installationTime);
                    Log.e(TAG, "onCreate:System.currentTimeMillis() " + System.currentTimeMillis());

                    if (installationTime < System.currentTimeMillis()) {
                        Intent myService = new Intent(context, LocationService.class);
                        mcontext.stopService(myService);
                        Log.e(TAG, "onCreate: SERVICE IS STOPPED ");
                    } else {
                        mcontext.startService(new Intent(context, LocationService.class));
                        Log.e(TAG, "onCreate: SERVICE IS RUNNING LocationService");
                    }
                }else {
                    mcontext.startService(new Intent(context, LocationService.class));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }


  
}


