//package com.eduvanzapplication.newUI.services;
//
//import android.Manifest;
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteException;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Build;
//import android.os.Environment;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.ActivityCompat;
//import android.telephony.TelephonyManager;
//import android.util.Log;
//
//import com.eduvanzapplication.SmsPojo;
//import com.eduvanzapplication.Util.CryptoHelper;
//import com.eduvanzapplication.Util.Globle;
//import com.eduvanzapplication.Utils;
//import com.eduvanzapplication.newUI.MainApplication;
//import com.eduvanzapplication.newUI.receiver.AlarmReceiverForFileUpload;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.Calendar;
//import java.util.Date;
//
//import static com.eduvanzapplication.newUI.MainApplication.TAG;
//
///**
// * Created by Vijay on 19/9/17.
// */
//
//public class MyServiceReadSmsbck extends Service {
//
//    Context context;
//    static String stringAllSmsContacts;
//    public static String latestSmsDate = "";
//    public static String userID = "", userMobileNo = "", userLoggedinID = "";
//    private LongOperation longOperation = null, longOperation2 = null, longOperation3 = null, longOperation4 = null;
//    public static String mobileNo = "", userid = "", data = "", page = "", userName = "", imeiNo = "", simImei = "", ipaddress = "";
//    public String smsTimeStamp, newsmstimestamp;
//    public static Date lDateSMS;
//    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    public static File f;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onCreate() {
//        Log.e(TAG, " ******************MyService READ SMS*******************");
//        context = this;
//        /** getting data from shared preference **/
//        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
//        userMobileNo = sharedPreferences.getString("mobile_no", "null");
////        userID = sharedPreferences.getString("logged_id", "null");
//
//        smsTimeStamp = sharedPreferences.getString("smsTimeStamp", "null");
//
//        try {
//            newsmstimestamp = sharedPreferences.getString("newsmstimestamp", "null");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//
////            lDateSMS = simpleDateFormat.parse(smsTimeStamp);
//            lDateSMS = simpleDateFormat.parse(newsmstimestamp);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        try {
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            imeiNo = telephonyManager.getDeviceId();
//            ipaddress = Utils.getIPAddress(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // adding pending intent
//        PendingIntent pendingIntent;
//        Intent intent = new Intent(context, AlarmReceiverForFileUpload.class);
//        pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        int interval = 2 * 60 * 60 * 1000;
////        int interval = 2 * 60 * 1000;
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
//
//    }
//
//
//    /**
//     * Read SMS
//     **/
//    public static void readSms(Context context, String userNo, String studentID) {
//        Context c = context;
//        SmsPojo objSms;
//        String message = "";
//        JSONObject outerOb = new JSONObject();
//
//        final String SMS_URI_INBOX = "content://sms/inbox";
//        final String SMS_URI_ALL = "content://sms/";
//        try {
//            Uri uri = Uri.parse(SMS_URI_INBOX);
//            // data which we need to show
//            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
//            Cursor cur = context.getContentResolver().query(uri, projection, null, null, null);
//            //count the number of result we get
//            int total = cur.getCount();
////            Log.e("MediaContent", "ReadSms :query" + cur.toString() + "\n" + total);
//            JSONArray json = new JSONArray();
//            int d = 0;
//
//            /** getting data from shared preference **/
//            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
//            userMobileNo = sharedPreferences.getString("mobile_no", "null");
//
////            This commented for testing
////            String callTimeStamps = sharedPreferences.getString("callTimeStamp", "null");
////            try {
////                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                lDateSMS = simpleDateFormat.parse(callTimeStamps);
//////                Log.e(TAG, "lDateCall::::: "+ lDateSMS);
////            } catch (ParseException e) {
////                e.printStackTrace();
////            }
////Date.from(Instant.ofEpochMilli(Long.parseLong(date)))
//            //LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(date)), ZoneId.systemDefault() ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//            if (cur.moveToFirst()) {
//                for (int i = 0; i < total; i++) {
//                    double final1 = ((double) i / total) * 100;
//                    int rounded = (int) Math.round(final1);
//
//                    objSms = new SmsPojo();
//                    String id = cur.getString(cur.getColumnIndexOrThrow("_id"));
//                    String peron = cur.getString(cur.getColumnIndexOrThrow("person"));
//                    objSms.set_address(cur.getString(cur.getColumnIndexOrThrow("address")));
//                    objSms.set_msg(cur.getString(cur.getColumnIndexOrThrow("body")));
//                    objSms.set_time(cur.getString(cur.getColumnIndexOrThrow("date")));
//                    String date = cur.getString(cur.getColumnIndexOrThrow("date"));
//                    String type = cur.getString(cur.getColumnIndexOrThrow("type"));
//                    cur.moveToNext();
//                    try {
//                        long dateOfSms = Long.valueOf(date);
//                        if (lDateSMS != null) {
//                            if (lDateSMS.getTime() < dateOfSms) {
//                                JSONObject mObject = new JSONObject();
//                                mObject.accumulate("from", objSms.get_address());
//                                mObject.accumulate("message", objSms.get_msg());
//                                mObject.accumulate("time", objSms.get_time());
//                                mObject.accumulate("type", type);
//                                mObject.accumulate("sms_id", id);
//                                json.put(mObject);
//                            }
//                        } else {
//                            JSONObject mObject = new JSONObject();
//                            mObject.accumulate("from", objSms.get_address());
//                            mObject.accumulate("message", objSms.get_msg());
//                            mObject.accumulate("time", objSms.get_time());
//                            mObject.accumulate("type", type);
//                            mObject.accumulate("sms_id", id);
//                            json.put(mObject);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                outerOb.accumulate("created_by_ip", ipaddress);
//                outerOb.accumulate("sim_serial_no", imeiNo);
//                outerOb.accumulate("imei", imeiNo);
//                outerOb.put("Sms_info", json);
//                message = outerOb.toString();
//
//            }
//            if (json.length() > 2) {
//                String cipher = "";
//                try {
//                    String newtimestamp = String.valueOf(new JSONArray(new JSONObject(message).getString("Sms_info")).getJSONObject(0).get("time"));
//                    String newtimestampdate = "";
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                        newtimestampdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(newtimestamp)), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                    }
//
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("newsmstimestamp", newtimestamp);
//                    editor.putString("newsmstimestamp", newtimestampdate);
//                    editor.apply();
//                    editor.commit();
//                    cipher = CryptoHelper.encrypt(message);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                StringBuffer sb = new StringBuffer();
//                //sb.append("&&");
//                sb.append(cipher);
//                //sb.append("&&");
//
//                mCreateAndSaveFile("saveSMS1.json", message);
////                mCreateAndSaveFile("saveSMS.json", String.valueOf(sb));
//            }
//
//            Log.e(TAG, "/*/*/*/*/*/*/*/*/*/*/*/*/SMS MESSAGES:/*/*/*/*/**/*/*/*/*/*/*/* " + "\n" + message);
//
//        } catch (SQLiteException ex)
//
//        {
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * END of Read SMS
//     **/
//
//    public static void mCreateAndSaveFile(String params, String mJsonResponse) {
//        try {
//            String path = "/storage/sdcard0/" + params;
//            final File dir = new File(Environment.getExternalStorageDirectory() + "/");
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            f = new File(dir, params);
//            f.getAbsolutePath();
//            FileWriter file = new FileWriter(f.getAbsolutePath());
//            file.write(mJsonResponse);
//            file.flush();
//            file.close();
//            mReadJsonData(params);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void mReadJsonData(final String filename) {
//        final File dir = new File(Environment.getExternalStorageDirectory() + "/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
//        final File f = new File(dir, filename);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.e(TAG, "run: getAbsolutePath:::SMS::: " + f.getAbsolutePath());
//////                uploadFile(f.getAbsolutePath(), filename);
//            }
//        }).start();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        longOperation = new LongOperation();
//        longOperation.execute("sms");
//        //start sticky means service will be explicity started and stopped
//        return Service.START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//
//    }
//
//    /**
//     * ---------------- ASYNC TASK --------------
//     **/
//    private class LongOperation extends AsyncTask<String, Void, Void> {
//
//        @Override
//        protected Void doInBackground(String... params) {
//            try {
//                readSms(context, userMobileNo, userID);
//            } catch (Exception e) {
//                String className = this.getClass().getSimpleName();
//                String name = new Object() {
//                }.getClass().getEnclosingMethod().getName();
//                String errorMsg = e.getMessage();
//                String errorMsgDetails = e.getStackTrace().toString();
//                String errorLine = String.valueOf(e.getStackTrace()[0]);
//                Globle.ErrorLog(context,className, name, errorMsg, errorMsgDetails, errorLine);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//        }
//    }
//
//
//    /**
//     * upload SMS file to server
//     **/
//    public static int uploadFile(final String selectedFilePath, String fileType) {
//        String a = fileType;
//        a = a.substring(0, a.lastIndexOf('.'));
//
//        StringBuffer sb;
//        long total = 0;
//        String urlup = MainActivity.mainUrl + "mobilescrap/send_message";//https://api.eduvanz.com/mobilescrap/send_message
////        String urlup = MainActivity.mainUrl + "mobilescrap/send_santosh";
//        int serverResponseCode = 0;
//
//        HttpURLConnection connection;
//        DataOutputStream dataOutputStream;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//
//        final int fileLength;
//
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1 * 1024 * 1024;
//        File selectedFile = new File(selectedFilePath);
//
//        Log.e(TAG, "uploadFile: " + selectedFilePath);
//        String[] parts = selectedFilePath.split("/");
//        final String fileName = parts[parts.length - 1];
//
//        if (!selectedFile.isFile()) {
//            Log.e("ReadSms", "run: " + "Source File Doesn't Exist: " + selectedFilePath);
//            return 0;
//        } else {
//            try {
//
//                FileInputStream fileInputStream = new FileInputStream(selectedFile);
//                URL url = new URL(urlup);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setDoInput(true);//Allow Inputs
//                connection.setDoOutput(true);//Allow Outputs
//                connection.setUseCaches(false);//Don't use a cached Copy
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Connection", "Keep-Alive");
//                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
//                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//                connection.setRequestProperty(a, selectedFilePath);
////                connection.setRequestProperty("file_name", "saveSMS");
//                Log.e("ReadSms", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());
//
//                //creating new dataoutputstream
//                dataOutputStream = new DataOutputStream(connection.getOutputStream());
//
//                //writing bytes to data outputstream
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + a + "\";filename=\""
////                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"saveCallLogs\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//
//                //returns no. of bytes present in fileInputStream
//                bytesAvailable = fileInputStream.available();
//                //selecting the buffer size as minimum of available bytes or 1 MB
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                //setting the buffer as byte array of size of bufferSize
//                buffer = new byte[bufferSize];
//                fileLength = bufferSize;
//                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//                Log.e(TAG, "uploadFile: TOTAL bytes to read " + bytesRead + "total" + bufferSize);
//                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
//                total = 0;
//
//                while (bytesRead > 0) {
//                    total += bytesRead;
//                    //write the bytes read from inputstream
//                    dataOutputStream.write(buffer, 0, bufferSize);
//                    Log.e("ReadSms", " here: \n\n" + buffer + "\n" + bufferSize);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                    Log.e(TAG, "uploadFile: " + bytesRead + total);
//                    Log.e(TAG, "uploadFile: percentage " + ((int) Math.round(total * 100 / fileLength)));
//                    // Publish the progress
//                    final int finalBytesRead = bytesRead;
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
//
//                    Log.e(TAG, "uploadFile: percentage " + ((int) Math.round(total * 100 / fileLength)));
////                            mDialog.setProgress((int) Math.round(total * 100 / fileLength));
////                        }
////                    });
//                }
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file_name\"" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes("1");
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
////                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
////                        + selectedFilePath + "\"" + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"mobileNo\";mobileNo=" + userMobileNo + "" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(userMobileNo);
//                dataOutputStream.writeBytes(lineEnd);
//                Log.e(TAG, "uploadFile:mobileNo--- sent " + userMobileNo);
//
//                //dataOutputStream.writeBytes(URLEncoder.encode("user_id", "UTF-8")
//                //        + "=" + URLEncoder.encode("1", "UTF-8"));
//
//                serverResponseCode = connection.getResponseCode();
//                Log.e("ReadSms", " here:server response \n\n" + serverResponseCode);
//                String serverResponseMessage = connection.getResponseMessage();
//                Log.e("ReadSms", " here: server message \n\n" + serverResponseMessage.toString() + "\n" + bufferSize);
//                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//                String output = "";
//                sb = new StringBuffer();
//
//                while ((output = br.readLine()) != null) {
//                    sb.append(output);
////                    Log.e("ReadSms", "uploadFile: SMS " + br);
////                    Log.e("ReadSms1", "Server Response is: SMS " + serverResponseMessage + ": " + serverResponseCode);
//                }
//                Log.e("ReadSms2", "uploadFile: " + sb.toString());
//
//                //response code of 200 indicates the server status OK
//                if (serverResponseCode == 200) {
//                    Log.e(TAG, "uploadFile: *********MY SERVICE READ SMS********** " + "\n" + sb.toString());
//
////                    Log.e("ReadSms", " here: SMS \n\n" + fileName);
//                    if(f.exists()){
//                        f.delete();
//                    }
//                }
//
//                //closing the input and output streams
//                fileInputStream.close();
//                dataOutputStream.flush();
//                dataOutputStream.close();
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Log.e(TAG, "uploadFile: " + "File Not Found");
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                Log.e(TAG, "uploadFile: " + "URL error!");
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.e(TAG, "uploadFile: " + "Cannot Read/Write File!");
//            }
//            return serverResponseCode;
//        }
//    }
//
//}
