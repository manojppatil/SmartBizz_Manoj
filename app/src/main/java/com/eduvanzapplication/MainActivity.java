package com.eduvanzapplication;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.eduvanzapplication.Util.FontsOverride;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Application {

    //6FoiXr+hADbzGL05ywYcs3BIXaFmmTad9kydjVsUDe64c6NiaIGTK7xgjWuthdiD1oep9okGUlzmVkdcpXRhuw== password
    public static String TAG = "EDUVANZ LOG";
//    public static String mainUrl = "http://139.59.32.234/eduvanzApi/"; //PRODUCTION
//    public static String mainUrl = " http://139.59.61.225/eduvanzApi/"; //TESTING
    public static String mainUrl = "http://159.89.204.41/eduvanzApi/"; //BETA

//    public static String mainUrl = "http://192.168.1.19/eduvanzapi/"; //Dharam
//    public static String mainUrl = "http://192.168.0.115/eduvanzapi/"; //Sachin
//    public static String mainUrl = "http://192.168.1.78/eduvanzapi/"; //Sachin
//    public static String mainUrl = "http://192.168.0.108/eduvanzapi/"; //Samir
//    public static String mainUrl = "http://192.168.0.101/eduvanzapi/index.php/"; //Vijay

//    public static String mainUrl = "https://api.eduvanz.com/"; ///////////////////L I V E//////////////

    public static String auth_token ="",lead_id ="", application_id = "",applicant_id ="" ;

    @Override
    public void onCreate() {
        super.onCreate();
        setFont();
//        generateHashkey();
    }

    private void setFont() {

        FontsOverride.setDefaultFont(getApplicationContext(), "MONOSPACE", "fonts/sourcesanspro_italic.ttf");//italic
        FontsOverride.setDefaultFont(getApplicationContext(), "NORMAL", "fonts/sourcesanspro_regular.ttf");//Regular
        FontsOverride.setDefaultFont(getApplicationContext(), "SANS", "fonts/sourcesanspro_light.ttf");//Light
        FontsOverride.setDefaultFont(getApplicationContext(), "SERIF", "fonts/sourcesanspro_semibold.ttf");//Semibold

    }

    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.eduvanzapplication",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e("Hash key", ""+ Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Teating", e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("Teating", e.getMessage(), e);
        }
    }
}
