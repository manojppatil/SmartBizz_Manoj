package com.eduvanzapplication.newUI.services;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.Util.CryptoHelper;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.receiver.LocationUploadForFileUpload;

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

/**
 * Created by nikhil on 26/10/17.
 **/

public class LocationService extends Service {
    private static final String TAG = "BOOMBOOMTESTGPS";
    private static final int LOCATION_INTERVAL = 60 * 60 * 1000;
    private static final float LOCATION_DISTANCE = 0f;
    public static Context mContext;
    public static String imeiNo = "", ipaddress = "", userMobileNo = "";
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };
    public LocationManager mLocationManager = null;

    public static void mCreateAndSaveFile(String params, String mJsonResponse) {
        try {
            final File dir = new File(Environment.getExternalStorageDirectory() + "/");
            if (!dir.exists()) {
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
        final File dir = new File(Environment.getExternalStorageDirectory() + "/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        final File f = new File(dir, filename);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: getAbsolutePath:::LOCATION::: " + f.getAbsolutePath());
                uploadFile(f.getAbsolutePath(), filename);
            }
        }).start();
    }

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
                Log.e("ReadSms", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());


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
                    Log.e("ReadSms", " here: \n\n" + buffer + "\n" + bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.e(TAG, "uploadFile: " + bytesRead + total);
                    Log.e(TAG, "uploadFile: percentage " + ((int) Math.round(total * 100 / fileLength)));
                    // Publish the progress

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

//                Log.e(TAG, "uploadFile: userMobileNo"+userMobileNo );

                //dataOutputStream.writeBytes(URLEncoder.encode("user_id", "UTF-8")
                //        + "=" + URLEncoder.encode("1", "UTF-8"));

                serverResponseCode = connection.getResponseCode();
                Log.e("ReadSms", " here:server response \n\n" + serverResponseCode);
                String serverResponseMessage = connection.getResponseMessage();
                Log.e("ReadSms", " here: server message \n\n" + serverResponseMessage.toString() + "\n" + bufferSize);
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output = "";
                sb = new StringBuffer();
// INSERT INTO `location_info` (`latitude_info`, `longitude_info`) VALUES (19.10956227, 72.88205647)
                // UPDATE `location_info` SET `sim_serial_info`='869822037834949',`imei`='869822037834949',`mobile_no`='7787838985',
                // `created_by_type`='4', `created_by_ip`='3232235784', `created_datetime`='2018-08-14 18:56:58'WHERE `id`='610'
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                Log.e("Location ", "uploadFile: " + sb.toString());

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {

//                    File fdelete = new File(uri.getPath());
//                    if (fdelete.exists()) {
//                        if (fdelete.delete()) {
//                            System.out.println("file Deleted :" + uri.getPath());
//                        } else {
//                            System.out.println("file not Deleted :" + uri.getPath());
//                        }
//                    }
                    Log.e(TAG, "uploadFile: *********MY SERVICE JSON RESONSE LOCATION********** " + "\n" + sb.toString());

//                    Log.e("ReadSms", " here: LOCATION LOG \n\n" + fileName);
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "uploadFile: " + "File Not Found");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(TAG, "uploadFile: " + "URL error!");

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "uploadFile: " + "Cannot Read/Write File!");
            }
            return serverResponseCode;
        }
    }

    @Override
    public void onCreate() {
        Log.e(TAG, " ******************MyService  LOCATION*******************");
        initializeLocationManager();
        mContext = getApplicationContext();
        SharedPreferences mShared = mContext.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userMobileNo = mShared.getString("mobile_no", "null");
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
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
            Log.e(MainApplication.TAG, "PHONE DATA " + "IMEINO:=" + imeiNo + "ipaddress:" + ipaddress);
        } catch (Exception e) {
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
//        LongOperation longOperation3 = new LongOperation();
//        longOperation3.execute("calllogs");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                locationData(mContext);
//            }
//        }).start();

        return START_STICKY;
    }

    private void locationData(Context mContext) {
        String logs = "";
        JSONArray jsonArray = new JSONArray();
        JSONObject outerOb = new JSONObject();
        JSONObject mObject = new JSONObject();
        try {
            mObject.accumulate("latitude_info", MainApplication.latitude);
            mObject.accumulate("longitude_info", MainApplication.longitde);
            jsonArray.put(mObject);
            outerOb.accumulate("created_by_ip", ipaddress);
            outerOb.accumulate("sim_serial_no", imeiNo);
            outerOb.accumulate("imei", imeiNo);
            outerOb.accumulate("mobileNo", userMobileNo);
            outerOb.put("location_info", jsonArray);

            Log.e(TAG, "/*/*/*/*/*/*/*/*/*/*/*/*/LOCATION:/*/*/*/*/**/*/*/*/*/*/*/* " + "\n" + jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray.length() >= 1) {
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

            mCreateAndSaveFile("saveLocationInfo.json", String.valueOf(sb));
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (mLocationManager != null) {
//            for (int i = 0; i < mLocationListeners.length; i++) {
//                try {
//                    mLocationManager.removeUpdates(mLocationListeners[i]);
//                } catch (Exception ex) {
//                    Log.i(TAG, "fail to remove location listners, ignore", ex);
//                }
//            }
//        }
    }

    private void initializeLocationManager() {
        try {
            if (mLocationManager == null) {
                mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            SharedPreferences mShared = mContext.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            mLastLocation.set(location);
            MainApplication.latitude = location.getLatitude();
            MainApplication.longitde = location.getLongitude();

            try {
            LongOperation longOperation3 = new LongOperation();
            longOperation3.execute("calllogs");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    locationData(mContext);
                }
            }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected Void doInBackground(String... params) {
            locationData(mContext);
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
