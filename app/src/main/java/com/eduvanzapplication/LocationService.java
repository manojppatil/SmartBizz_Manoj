package com.eduvanzapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TimeUtils;

import com.eduvanzapplication.newUI.MainApplication;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vijay on 10/06/19.
 **/

public class LocationService extends Service {
    private static final String TAG = "BOOMBOOMTESTGPS";
    private static final int LOCATION_INTERVAL = 60 * 1000;
    private static final float LOCATION_DISTANCE = 0f;
    public static Context mContext;
    public static String userMobileNo;
    public static String imeiNo = "", ipaddress = "";
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };
    public LocationManager mLocationManager = null;

    @Override
    public void onCreate() {
//        Log.e(TAG, " ******************MyService  LOCATION*******************");
        initializeLocationManager();
        mContext = getApplicationContext();
        SharedPreferences mShared = mContext.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userMobileNo = mShared.getString("mobile_no", "null");

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

        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            imeiNo = telephonyManager.getDeviceId();
            ipaddress = Utils.getIPAddress(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // adding pending intent of 6 Hours.
        PendingIntent pendingIntent;
        Intent intent = new Intent(mContext, LocationService.class);
        pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        int interval = 60 * 1000;
//            int interval = 2 * 1000;
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
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

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(mContext, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }
            //otp,mobile,lead_id,online,signed_by_id	in online key i want array which stores as following:
            // IP,city,regioin,country,location,latitute,longitude,ISP_provider,pincode,browser_name,platform
//            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only,// check with max available address lines by getMaxAddressLineIndex()
            String city = "";
            try {
                city = addresses.get(0).getLocality();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String state = "";
            try {
                state = addresses.get(0).getAdminArea();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String country = "";
            try {
                country = addresses.get(0).getCountryName();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String postalCode = "";
            try {
                postalCode = addresses.get(0).getPostalCode();
            } catch (Exception e) {
                e.printStackTrace();
            }

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("city", city);
            editor.putString("state", state);
            editor.putString("country", country);
            editor.putString("postalCode", postalCode);
            editor.putString("ipaddress", ipaddress);
            editor.putString("latitude", String.valueOf(location.getLatitude()));
            editor.putString("longitde", String.valueOf(location.getLongitude()));
            editor.apply();
            editor.commit();


            Log.e(TAG, "Location Time: " + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()));

            try {
                if(sharedPreferences.getString("city", "") != "" && sharedPreferences.getString("state", "") != ""
                        && sharedPreferences.getString("country", "") != "" && sharedPreferences.getString("postalCode", "") != ""
                        && sharedPreferences.getString("latitude", "") != "" && sharedPreferences.getString("longitde", "") != ""
                        && sharedPreferences.getString("ipaddress", "") != "") {
                    onDestroy();
                }
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
}
