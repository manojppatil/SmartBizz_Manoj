package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueButton;
import com.truecaller.android.sdk.TrueClient;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingInWithTruecaller extends AppCompatActivity implements ITrueCallback {

    private TrueClient mTrueClient;
    private String mTruecallerRequestNonce;
    TextView txtnote, textViewToolbar;
    MainApplication mainApplication;
    static Context mContext;
    Button button;
    AppCompatActivity mActivity;
    String mobileNO = "";
    public int GET_MY_PERMISSION = 1, permission, permission1, permission2, permission3,
            permission4, permission5, permission6, policyAgreementStatus;
    public String verificationtype = "Verified by Truecaller SDK";
    private int batterylevel = 0;
    public int scale = 0;
    public String network_provider_0 = "";
    public String network_provider_1 = "";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_truelogin);
            mActivity = this;
            mContext = this;
            mainApplication = new MainApplication();

            try {
                Bundle bundle = getIntent().getExtras();
                mobileNO = bundle.getString("mobile_no_val", "");
            } catch (Exception e) {
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

            if (!isAccessGranted()) {
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
                                                Manifest.permission.RECEIVE_SMS,
                                                Manifest.permission.READ_SMS,
                                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.READ_PHONE_STATE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.ACCESS_FINE_LOCATION},
                                        GET_MY_PERMISSION);

                            } else {
                                apiCall();
                            }
                        } else {
                            apiCall();
                        }
                    } catch (Exception e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(SingInWithTruecaller.this,className, name, errorMsg, errorMsgDetails, errorLine);
                    }

                }
            });
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SingInWithTruecaller.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }

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

    private void setLanguage() {
        try {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(SingInWithTruecaller.this);
            builderSingle.setIcon(R.drawable.eduvanz_logo_new);
            builderSingle.setTitle("Select One Name:-");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SingInWithTruecaller.this, android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Hardik");
            arrayAdapter.add("Archit");
            arrayAdapter.add("Jignesh");
            arrayAdapter.add("Umang");
            arrayAdapter.add("Gatti");

            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    AlertDialog.Builder builderInner = new AlertDialog.Builder(SingInWithTruecaller.this);
                    builderInner.setMessage(strName);
                    builderInner.setTitle("Your Selected Item is");
                    builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builderInner.show();
                }
            });
            builderSingle.show();
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SingInWithTruecaller.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(SingInWithTruecaller.this,
                new String[]{Manifest.permission.READ_SMS},
                GET_MY_PERMISSION);
    }

    public void apiCall() {
        /** API CALL GET OTP**/

        try {
            mTrueClient.getTruecallerUserProfile(SingInWithTruecaller.this);
            button.setEnabled(false);
        } catch (Exception e) {
//            Log.i(MainApplication.TAG, "truecaller: " + e.getStackTrace());

            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SingInWithTruecaller.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length <= 0) {
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED &&
                         grantResults[5] == PackageManager.PERMISSION_GRANTED) {
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

        try {
            StringBuilder sr = new StringBuilder();
            if (trueProfile.verificationMode != null) {
                sr.append("Verification mode: ").append(trueProfile.verificationMode).append("\n");
            }
            if (trueProfile.verificationTimestamp != 0L) {
                sr.append("Verification Time: ").append(trueProfile.verificationTimestamp).append("\n");
            }

            sr.append("Sim changed: ").append(trueProfile.isSimChanged).append("\n");
            sr.append("RequestNonce: ").append(trueProfile.requestNonce).append("\n");

            setData(trueProfile);
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SingInWithTruecaller.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    @Override
    public void onFailureProfileShared(@NonNull TrueError trueError) {
        Intent intent = new Intent(SingInWithTruecaller.this, GetMobileNo.class);
        startActivity(intent);
        finish();
//        Toast.makeText(this, "SignIn Failed", Toast.LENGTH_LONG).show();

    }

    public void setData(TrueProfile trueProfile) {  //{"message":"update mandate","is_update_mandate":1}
        try {

            try {
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
                IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent batteryStatus = SingInWithTruecaller.this.registerReceiver(null, ifilter);

                batterylevel = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

//        float batteryPct = batterylevel / (float)scale;

                SubscriptionManager subscriptionManager = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    subscriptionManager = (SubscriptionManager) getApplicationContext().getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                }

                List<SubscriptionInfo> subscriptionInfoList = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();

                    try {
                        network_provider_0 = String.valueOf(subscriptionInfoList.get(0).getDisplayName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        network_provider_0 = network_provider_0 + "_" + subscriptionInfoList.get(0).getSimSlotIndex();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        network_provider_1 = String.valueOf(subscriptionInfoList.get(1).getDisplayName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        network_provider_1 = network_provider_1 + "_" + subscriptionInfoList.get(1).getSimSlotIndex();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url = MainApplication.mainUrl + "truecallerresponse/insert";//http://192.168.0.110/eduvanzapi/pqform/thirdPartyGenerateOtpCode
            Map<String, String> params = new HashMap<String, String>();

            params.put("avatarUrl", String.valueOf(trueProfile.avatarUrl));
            params.put("city", String.valueOf(trueProfile.city));
            params.put("companyName", String.valueOf(trueProfile.companyName));
            params.put("countryCode", String.valueOf(trueProfile.countryCode));
            params.put("email", String.valueOf(trueProfile.email));
            params.put("facebookId", String.valueOf(trueProfile.facebookId));
            params.put("firstName", String.valueOf(trueProfile.firstName));
            params.put("gender", String.valueOf(trueProfile.gender));
            params.put("isAmbassador", String.valueOf(trueProfile.isAmbassador));
            params.put("isSimChanged", String.valueOf(trueProfile.isSimChanged));
            params.put("isTrueName", String.valueOf(trueProfile.isTrueName));
            params.put("jobTitle", String.valueOf(trueProfile.jobTitle));
            params.put("lastName", String.valueOf(trueProfile.lastName));
            params.put("payload", String.valueOf(trueProfile.payload));
            params.put("phoneNumber", String.valueOf(trueProfile.phoneNumber));
            params.put("requestNonce", String.valueOf(trueProfile.requestNonce));
            params.put("signature", String.valueOf(trueProfile.signature));
            params.put("street", String.valueOf(trueProfile.street));
            params.put("twitterId", String.valueOf(trueProfile.twitterId));
            params.put("url", String.valueOf(trueProfile.url));

//            if (String.valueOf(trueProfile.verificationTimestamp) == null || String.valueOf(trueProfile.verificationTimestamp) == "") {
//                verification = "Verified by Truecaller SDK";
//            } else {
//                verification = String.valueOf(trueProfile.verificationTimestamp);
//            }

            params.put("verificationMode", verificationtype);
            params.put("verificationTimestamp", String.valueOf(trueProfile.verificationTimestamp));
            params.put("zipcode", String.valueOf(trueProfile.zipcode));

            String appVersion ="",deviceVersion ="",device ="";
            try {
                PackageManager manager = SingInWithTruecaller.this.getPackageManager();
                PackageInfo info = manager.getPackageInfo(
                        SingInWithTruecaller.this.getPackageName(), 0);
                appVersion = info.versionName;
                deviceVersion = String.valueOf(Build.VERSION.SDK_INT);
                device = String.valueOf(Build.MODEL);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String ipaddress ="";
            try {
                ipaddress = Utils.getIPAddress(true);
            } catch (Exception e) {
                e.printStackTrace();
            }

            params.put("handset", device);
            params.put("network_provider", network_provider_0 + "," + network_provider_1);
            params.put("signal_strength", "");
            params.put("battery_strength", String.valueOf(batterylevel));
            params.put("last_connected_tower", "");
            params.put("wifi_connected_network", "");
            params.put("app_version", appVersion);
            params.put("device_version", deviceVersion);
            params.put("ip_address", ipaddress);

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mobile_no", trueProfile.phoneNumber);
            editor.apply();
            editor.commit();

            if (!Globle.isNetworkAvailable(SingInWithTruecaller.this)) {
                Toast.makeText(SingInWithTruecaller.this, getText(R.string.please_check_your_network_connection), Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(SingInWithTruecaller.this, url, mActivity, null, "updateTrueData", params, MainApplication.auth_token);
            }

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SingInWithTruecaller.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void UpdateTrueData(JSONObject jsonData) {  //{"message":"update mandate","is_update_mandate":1}
        try {

            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("otp_done", "1");
            editor.apply();
            editor.commit();

            Intent intent = new Intent(SingInWithTruecaller.this, DashboardActivity.class);
            startActivity(intent);
            finish();

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(SingInWithTruecaller.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }
}
