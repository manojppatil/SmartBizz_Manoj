package com.smartbizz;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

//import com.smartbizz.Util.FontsOverride;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Application {

    public static String TAG = "SMARTBIZZ LOG";


    public static String auth_token ="", lead_id ="", application_id = "", applicant_id ="" ;
    @Override
    public void onCreate() {
        super.onCreate();
//        setFont();
//      generateHashkey();
    }

//    private void setFont() {
//
//        FontsOverride.setDefaultFont(getApplicationContext(), "MONOSPACE", "fonts/sourcesanspro_italic.ttf");//italic
//        FontsOverride.setDefaultFont(getApplicationContext(), "NORMAL", "fonts/sourcesanspro_regular.ttf");//Regular
//        FontsOverride.setDefaultFont(getApplicationContext(), "SANS", "fonts/sourcesanspro_light.ttf");//Light
//        FontsOverride.setDefaultFont(getApplicationContext(), "SERIF", "fonts/sourcesanspro_semibold.ttf");//Semibold
//    }

    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.smartbizz",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e("Hash key", ""+ Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Testing", e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("Testing", e.getMessage(), e);
        }
    }
}


