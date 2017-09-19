package com.eduvanz;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.eduvanz.pqformfragments.PqFormFragment1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        generateHashkey();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        generateHashkey();
//        Log.e("--------CALL---------", "");
//        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_pqform,new PqFormFragment1()).commit();
//    }

    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.eduvanz",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.e("Hash key", ""+ Base64.encodeToString(md.digest(),Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Teating", e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.e("Teating", e.getMessage(), e);
        }
    }
}
