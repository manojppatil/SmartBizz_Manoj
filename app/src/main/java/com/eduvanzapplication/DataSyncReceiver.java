package com.eduvanzapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.eduvanzapplication.newUI.newViews.DashboardActivity;

import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by Vijay on 05-09-17.
 */

public class DataSyncReceiver extends BroadcastReceiver {

    Context context;
    SharedPreferences sharedPreferences;
    @Override
    public void onReceive(Context mcontext, Intent intent) {
        context = mcontext;

        String action = intent.getAction();

        Boolean dataSynced = intent.getBooleanExtra("DataSynced",false);
        String algo360_datasync = String.valueOf(intent.getBooleanExtra("DataSynced",false));
        Log.e("Receiver", "Data synced: " + dataSynced);

        //            if(dataSynced) {
        DashboardActivity.saveAlgo360();
//                saveAlgo360(context, student_id,userMobileNo,userEmail, String.valueOf(true));
//        sharedPreferences = context.getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("Data synced", String.valueOf(dataSynced));
//        editor.commit();
//            }
    }

}


