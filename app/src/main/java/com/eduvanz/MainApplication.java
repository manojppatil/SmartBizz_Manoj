package com.eduvanz;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentResolver;
import android.content.Context;
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

import com.eduvanz.pqformfragments.SuccessAfterPQForm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by projetctheena on 3/8/17.
 */

public class MainApplication {

    public static String TAG = "EDUVANZ LOG";
    public static String mainUrl = "http://139.59.32.234/eduvanzApi/";
    public static int previous, previousfragment3;
    public static String mainapp_courseID = "", mainapp_instituteID = "";
    public static String mainapp_locationID = "";
    public static String mainapp_loanamount = "";
    public static String mainapp_fammilyincome = "";
    public static String mainapp_doccheck = "";
    public static String mainapp_professioncheck = "";
    public static String mainapp_currentCity = "";

    public static String indianCurrencyFormat(String rupees) {
        double a = Double.parseDouble(rupees);

//        DecimalFormat format = new DecimalFormat("0.#");
//        System.out.println(format.format(a));

        Locale indian = new Locale("hi", "IN");
        NumberFormat indianFormat = NumberFormat.getCurrencyInstance(indian);
        String finalAmount = String.valueOf(indianFormat.format(a));
        return finalAmount;
    }

}
