package com.eduvanzapplication.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Vijay on 17/7/17.
 */

public class GettingDeviceTokenService extends FirebaseInstanceIdService {

    public void onTokenRefresh() {
        String DeviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("DeviceToken ==> ",  DeviceToken);
    }
}
