package com.eduvanzapplication.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;

import com.eduvanzapplication.Util.Paytm;
import com.eduvanzapplication.uploaddocs.PathFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class Globle {
    public static void setInstance(Globle instance) {
        Globle.instance = instance;
    }

    private static Globle instance;

    public Paytm paytm;

    public static final String status = "1"; //For Success
    public static final String payment_platform = "2"; //(1-web, 2-app)
    public static final String payment_partner = "1"; //(1-paytm, 2-atom)

    //payment_platform = 2 (1-web, 2-app)
    //payment_partner = 1 (1-paytm, 2-atom)

    public static synchronized Globle getInstance() {
        if (instance == null) {
            instance = new Globle();
        }
        return instance;
    }

    public static String formatDate(long milliseconds) /* This is your topStory.getTime()*1000 */ {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }

    public static void appendLog(String text) {
        File logFile = new File("sdcard/" +
                "Edutest.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String loadJSONFromAsset(Context context, String filepath) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("saveContacts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable();
    }

}
