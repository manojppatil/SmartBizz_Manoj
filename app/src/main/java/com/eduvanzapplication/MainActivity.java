package com.eduvanzapplication;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.eduvanzapplication.Util.FontsOverride;
import com.eduvanzapplication.Util.TypefaceUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(getApplicationContext(), "MONOSPACE", "fonts/sourcesanspro_italic.ttf");//italic
        FontsOverride.setDefaultFont(getApplicationContext(), "NORMAL", "fonts/sourcesanspro_regular.ttf");//Regular
        FontsOverride.setDefaultFont(getApplicationContext(), "SANS", "fonts/sourcesanspro_light.ttf");//Light
        FontsOverride.setDefaultFont(getApplicationContext(), "SERIF", "fonts/sourcesanspro_semibold.ttf");//Semibold

//        generateHashkey();
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
