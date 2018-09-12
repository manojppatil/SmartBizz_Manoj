package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SingInWithTruecaller extends AppCompatActivity implements ITrueCallback {

    private TrueClient mTrueClient;
    private String mTruecallerRequestNonce;
    TextView txtnote,textViewToolbar;
    MainApplication mainApplication;
    static Context mContext;
    Button button;
    AppCompatActivity mActivity;
    String mobileNO="";
    public int GET_MY_PERMISSION = 1, permission, permission1, permission2, permission3,
            permission4, permission5, permission6, policyAgreementStatus;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truelogin);
        mActivity = this;
        mContext = this;
        mainApplication = new MainApplication();

        try {
            Bundle bundle = getIntent().getExtras();
            mobileNO = bundle.getString("mobile_no_val", "");
        }catch (Exception e){
            e.printStackTrace();
        }

        textViewToolbar = (TextView) findViewById(R.id.textView_validateotp);
        mainApplication.applyTypefaceBold(textViewToolbar, mContext);
//        txtnote = (TextView) findViewById(R.id.txtnote);

        TrueButton trueButton = (TrueButton) findViewById(R.id.com_truecaller_android_sdk_truebutton);
        button = (Button) findViewById(R.id.btnOneTapSignIn);

        boolean usable = trueButton.isUsable();
        mainApplication.applyTypeface(button, mContext);

        if (usable) {
            mTrueClient = new TrueClient(SingInWithTruecaller.this, this);
            mTrueClient.setReqNonce("1235678Min");
            trueButton.setTrueClient(mTrueClient);
            trueButton.setVisibility(View.GONE);
        } else {
            trueButton.setVisibility(View.GONE);
            Intent intent = new Intent(SingInWithTruecaller.this,
                    GetMobileNo.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            SingInWithTruecaller.this.finish();

        }

        if(!isAccessGranted()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Build.VERSION.SDK_INT >= 23) {

                        permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_SMS);

                        if (permission != PackageManager.PERMISSION_GRANTED)

                        {//Direct Permission without disclaimer dialog
                            ActivityCompat.requestPermissions(SingInWithTruecaller.this,
                                    new String[]{Manifest.permission.READ_CONTACTS,
                                            Manifest.permission.READ_SMS,
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_PHONE_STATE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.ACCESS_FINE_LOCATION},
                                    GET_MY_PERMISSION);

                        } else {
                            apiCall();
                        }
                    }else
                    {
                        apiCall();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        mTruecallerRequestNonce = mTrueClient.generateRequestNonce();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(SingInWithTruecaller.this,
                new String[]{Manifest.permission.READ_SMS},
                GET_MY_PERMISSION);
    }

    public void apiCall(){
        /** API CALL GET OTP**/

        try {
            mTrueClient.getTruecallerUserProfile(SingInWithTruecaller.this);
            button.setEnabled(false);
        } catch (Exception e) {
            Log.i(MainApplication.TAG, "truecaller: " + e.getStackTrace());
//            Globle.appendLog(e.getMessage());
//            Globle.appendLog(String.valueOf(e.getCause()));
//            Globle.appendLog(String.valueOf(e.getStackTrace()));
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length <= 0) {
                }
                else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    apiCall();
                } else {
                    //not granted
//                    Log.e(MainApplication.TAG, "not granted: Dashboard " + grantResults[0]);
                    {
                        // Permission denied.
                        // Notify the user via a SnackBar that they have rejected a core permission for the
                        // app, which makes the Activity useless. In a real app, core permissions would
                        // typically be best requested during a welcome-screen flow.
                        // Additionally, it is important to remember that a permission might have been
                        // rejected without asking the user for permission (device policy or "Never ask
                        // again" prompts). Therefore, a user interface affordance is typically implemented
                        // when permissions are denied. Otherwise, your app could appear unresponsive to
                        // touches or interactions which have required permissions.
                        //                    Toast.makeText(this, R.string.permission_denied_explanation, Toast.LENGTH_LONG).show();
                        //                    finish();
                        Snackbar.make(
                                findViewById(R.id.rootViews),
                                R.string.permission_denied_explanation,
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.settings, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Build intent that displays the App settings screen.
                                        Intent intent = new Intent();
                                        intent.setAction(
                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package",
                                                BuildConfig.APPLICATION_ID, null);
                                        intent.setData(uri);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (null != mTrueClient && mTrueClient.onActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccesProfileShared(@NonNull TrueProfile trueProfile) {

        StringBuilder sr = new StringBuilder();
        if (trueProfile.verificationMode != null) {
            sr.append("Verification mode: ").append(trueProfile.verificationMode).append("\n");
        }
        if (trueProfile.verificationTimestamp != 0L) {
            sr.append("Verification Time: ").append(trueProfile.verificationTimestamp).append("\n");
        }

        sr.append("Sim changed: ").append(trueProfile.isSimChanged).append("\n");
        sr.append("RequestNonce: ").append(trueProfile.requestNonce).append("\n");

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("otp_done", "1");
        editor.putString("mobile_no", trueProfile.phoneNumber);
        editor.apply();
        editor.commit();

        Intent intent = new Intent(SingInWithTruecaller.this, DashboardActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Intent intent = new Intent(SingInWithTruecaller.this, GetMobileNo.class);
        startActivity(intent);
        finish();
//        Toast.makeText(this, "SignIn Failed", Toast.LENGTH_LONG).show();

    }
}
