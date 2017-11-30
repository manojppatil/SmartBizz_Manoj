package com.eduvanz.newUI.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;


import com.eduvanz.MainApplication;
import com.eduvanz.Utils;
import com.eduvanz.newUI.receiver.AlarmReceiverForFileUpload;
import com.eduvanz.newUI.receiver.LocationUploadForFileUpload;
import com.eduvanz.volley.VolleyCall;

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
import java.util.HashMap;
import java.util.Map;

import static com.eduvanz.MainApplication.TAG;

/**
 * Created by nikhil on 26/10/17.
 */

public class LocationService extends Service
{
    private static final String TAG = "BOOMBOOMTESTGPS";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 600000;
    private static final float LOCATION_DISTANCE = 1f;
    public static Context mContext;
    public static String userId,imeiNo = "",appInstallationTimeStamp="", simImei = "", ipaddress = "",userMobileNo="";

    private class LocationListener implements android.location.LocationListener
    {
        Location mLastLocation;

        public LocationListener(String provider)
        {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location)
        {
            SharedPreferences mShared= mContext.getSharedPreferences("UserData", Context.MODE_PRIVATE);
//            userMobileNo = mShared.getString("mobile_no", "null");

//            userId = mShared.getString("user_id", null);
            Log.e(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            MainApplication.latitude=location.getLatitude();
            MainApplication.longitde=location.getLongitude();

//            Map<String, String> params = new HashMap<String, String>();
//            params.put("user_id",userId);
////            params.put("emergency_id",MainApplication.emergencyID);
//            params.put("latitude",String.valueOf(location.getLatitude()));
//            params.put("longitude",String.valueOf(location.getLongitude()));
//            String url = MainApplication.mainUrl + "/mobilescrap/send_message_post";
//            VolleyCall mCallv= new VolleyCall();
//            mCallv.sendRequest(mContext,url,null,null,"update-location",params);
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[] {
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };


    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        Log.e(MainApplication.TAG, "Service onStartCommand");
        Log.e(TAG, "selectOperations: " );
        LongOperation longOperation3 = new LongOperation();
        longOperation3.execute("calllogs");
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        Log.e(TAG, "onCreate SET PENDING INTENT " );
        initializeLocationManager();
        mContext=getApplicationContext();
        SharedPreferences mShared= mContext.getSharedPreferences("UserData", Context.MODE_PRIVATE);
//        appInstallationTimeStamp = mShared.getString("appInstallationTimeStamp", "");
        userMobileNo = mShared.getString("mobile_no", "null");
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            imeiNo = telephonyManager.getDeviceId();
            ipaddress = Utils.getIPAddress(true);
            Log.e(MainApplication.TAG, "PHONE DATA " + "IMEINO:=" + imeiNo + "ipaddress:" + ipaddress );
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

        // adding pending intent of 6 Hours.
        PendingIntent pendingIntent;
        Intent intent = new Intent(mContext, LocationUploadForFileUpload.class);
        pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        int interval = 6 * 60 * 60 * 1000;
//            int interval = 2 * 1000;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);

    }

    @Override
    public void onDestroy()
    {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public static void mCreateAndSaveFile(String params, String mJsonResponse) {
        Log.e(MainApplication.TAG, " mCreateAndSaveFile:"+mJsonResponse );
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
    /** ---------------- ASYNC TASK --------------**/
    private class LongOperation extends AsyncTask<String, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected Void doInBackground(String... params) {
            Log.e(TAG, "doInBackground: callLogs "+params[0] );
            locationData(mContext);
            return null;
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private void locationData(Context mContext) {
        Log.e(MainApplication.TAG, " callLogs: 444444444444444444444444444444444" );
        String phNumber="", callType="", callDuration="", logs="";
        JSONArray jsonArray = new JSONArray();
        JSONObject outerOb = new JSONObject();
        JSONObject mObject = new JSONObject();
        try {
            mObject.accumulate("latitude_info", MainApplication.latitude);
            mObject.accumulate("longitude_info",MainApplication.longitde);
            jsonArray.put(mObject);
            outerOb.accumulate("created_by_ip", ipaddress);
            outerOb.accumulate("sim_serial_no", imeiNo);
            outerOb.accumulate("imei", imeiNo);
            outerOb.accumulate("student_id", "");
            outerOb.accumulate("mobileNo", userMobileNo);
            outerOb.put("location_info", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "callLogs: "+jsonArray );
        if(jsonArray.length()>=1)
        {
            logs = outerOb.toString();
            mCreateAndSaveFile("saveLocationInfo.json", logs);
        }
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

                Log.e(TAG, "uploadFile: userMobileNo"+userMobileNo );

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
                    Log.e(TAG, "uploadFile:LOCATION LOG "+sb.toString() );

                    Log.e("ReadSms", " here: LOCATION LOG \n\n" + fileName);
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
