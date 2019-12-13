package com.eduvanzapplication.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

import com.eduvanzapplication.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import static com.eduvanzapplication.database.DBAdapter.ExecuteSql;

public class Globle {
    public static void setInstance(Globle instance) {
        Globle.instance = instance;
    }

    private static Globle instance;

    public Paytm paytm;

    public static final String status = "1"; //For Success
    public static final String payment_platform = "2"; //(1-web, 2-app)
    public static final String payment_partner = "1"; //(1-paytm, 2-atom)
    public static DecimalFormat decimalFormat = new DecimalFormat("##,##,##0.00");

    public static final String clientCode= "EDUV8729"; //Sandbox EDDU2319 Prduction EDUV8729
    public static final String apiKey = "762753152658161";//	Sandbox gq991xYd Production 762753152658161
    public static final String salt = "Jge5fSh37nfKLsy88L"; //Sandbox hj17fhd8 Production Jge5fSh37nfKLsy88L
    public static final String screenTitle = "Eduvanz eKYC";

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

    public static String dateFormater4(String date) {
        String formatedDate = null;
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yy"); //07/15/2016
        try {
            Date oneWayTripDate = input.parse(date);
            formatedDate = output.format(oneWayTripDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatedDate;
    }

    public static String dateFormater5(String date) {
        String formatedDate = null;
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM yy"); //07/15/2016
        try {
            Date oneWayTripDate = input.parse(date);
            formatedDate = output.format(oneWayTripDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }

    public static String dateFormaterForKarza(String date) {
        String formatedDate = null;
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy"); //07/15/2016
        try {
            Date oneWayTripDate = input.parse(date);
            formatedDate = output.format(oneWayTripDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatedDate;
    }

    public static String panPattern = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern1 = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateAadharNumber(String aadharNumber){
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if(isValidAadhar){
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }

    public static void ErrorLog(Context context, String className, String name, String errorMsg, String errorMsgDetails, String errorLine) {
        try {
            String userID = "", versionName = "", osVersion = "", device = "", ipaddress = "";
            String currentDateTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ipaddress = Utils.getIPAddress(true);

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                userID = sharedPreferences.getString("logged_id", "null");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(
                        context.getPackageName(), 0);
                versionName = info.versionName;
                osVersion = String.valueOf(Build.VERSION.SDK_INT);
                device = String.valueOf(Build.MODEL);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (!Globle.isNetworkAvailable(context)) {

                String LocalsSql = "INSERT INTO ErrorLog (errorLogID, appName, appVersion, userID, errorDate, moduleName, " +
                        "methodName, errorMessage, errorMessageDtls, OSVersion, IPAddress, deviceName,lineNumber, ISSaved, ISUploaded) VALUES" +
                        " ('" + UUID.randomUUID().toString() + "'," +
                        " '" + "Eduvanz Student App" + "'," +
                        " '" + versionName + "'," +
                        " '" + userID + "'," +
                        " '" + currentDateTimeString + "'," +
                        " '" + className + "'," +
                        " '" + name + "'," +
                        " '" + errorMsg + "'," +
                        " '" + errorMsgDetails + "'," +
                        " '" + osVersion + "'," +
                        " '" + ipaddress + "'," +
                        " '" + device + "'," +
                        " '" + errorLine + "'," +
                        " '" + true + "'," +
                        "'" + "false" + "')";

                ExecuteSql(LocalsSql);

            } else {


                String LocalsSql = "INSERT INTO ErrorLog (errorLogID, appName, appVersion, userID, errorDate, moduleName, " +
                        "methodName, errorMessage, errorMessageDtls, OSVersion, IPAddress, deviceName,lineNumber, ISSaved, ISUploaded) VALUES" +
                        " ('" + UUID.randomUUID().toString() + "'," +
                        " '" + "Eduvanz Student App" + "'," +
                        " '" + versionName + "'," +
                        " '" + userID + "'," +
                        " '" + currentDateTimeString + "'," +
                        " '" + className + "'," +
                        " '" + name + "'," +
                        " '" + errorMsg + "'," +
                        " '" + errorMsgDetails + "'," +
                        " '" + osVersion + "'," +
                        " '" + ipaddress + "'," +
                        " '" + device + "'," +
                        " '" + errorLine + "'," +
                        " '" + true + "'," +
                        "'" + "false" + "')";

                ExecuteSql(LocalsSql);
            }

        } catch (Exception e) {
        }
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static void appendLog(String text) {
        File logFile = new File("sdcard/" +
                "EduPayTest.txt");
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

    public static AlertDialog dialog(Context context, String messege, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(title);
        builder.setMessage(messege);
        //  builder.setPositiveButton("Ok", null);
        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.getWindow().getAttributes();
        return builder.create();
    }

}
