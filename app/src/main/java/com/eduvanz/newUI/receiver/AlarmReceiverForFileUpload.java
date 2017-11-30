package com.eduvanz.newUI.receiver;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.eduvanz.DBHandler;
import com.eduvanz.MainApplication;
import com.eduvanz.SmsPojo;
import com.eduvanz.Utils;
import com.eduvanz.newUI.services.LocationService;
import com.eduvanz.newUI.services.MyServiceCallLog;
import com.eduvanz.newUI.services.MyServiceCallStats;
import com.eduvanz.newUI.services.MyServiceContacts;
import com.eduvanz.newUI.services.MyServiceReadSms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.eduvanz.MainApplication.TAG;

/**
 * Created by Darsh on 05-09-17.
 */

public class AlarmReceiverForFileUpload extends BroadcastReceiver {
  
    Context context;
    static String stringAllSmsContacts;
    public static String latestSmsDate = "";
    @Override
    public void onReceive(Context mcontext, Intent intent) {
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


