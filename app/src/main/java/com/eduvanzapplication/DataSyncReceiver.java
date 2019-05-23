package com.eduvanzapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by Vijay on 05-09-17.
 */

public class DataSyncReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context mcontext, Intent intent) {
        context = mcontext;

        String action = intent.getAction();

        Boolean dataSynced = intent.getBooleanExtra("DataSynced",false);
        String algo360_datasync = String.valueOf(intent.getBooleanExtra("DataSynced",false));
        Log.e("Receiver", "Data synced: " + dataSynced);
        Log.e("Receiver", "Data Action: " + action);



    }
  
}


